package com.dev.share.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

/**
 * 项目: SF_Common
 * 描述: HTTP|HTTPS请求
 * 作者: zhangyi183790
 * 时间: 2019年3月20日 下午1:52:54
 * 版本: v1.0
 * JDK: 1.8
 */
public class HttpUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	/**
	 * http客户端
	 */
	private static CloseableHttpClient client = HttpClients.createDefault();
	/**
	 * HTTP协议:http://
	 */
	public final static String PROTOCOL_HTTP = "http://";
	/**
	 * HTTPS协议:https://
	 */
	public final static String PROTOCOL_HTTPS = "https://";
	/**
	 * Get请求
	 */
	public final static String METHOD_GET = "GET";
	/**
	 * Post请求
	 */
	public final static String METHOD_POST = "POST";
	/**
	 * Head请求
	 */
	public final static String METHOD_HEAD = "HEAD";
	/**
	 * Options请求
	 */
	public final static String METHOD_OPTIONS = "OPTIONS";
	/**
	 * Put请求
	 */
	public final static String METHOD_PUT = "PUT";
	/**
	 * Delete请求
	 */
	public final static String METHOD_DELETE = "DELETE";
	/**
	 * Trace请求
	 */
	public final static String METHOD_TRACE = "TRACE";

	/**
	 * @param proxyHost 代理地址
	 * @param port      代理端口
	 * @param account   认证账号
	 * @param password  认证密码
	 */
	public static void auth(String proxyHost, int port, final String account, final String password) {
		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", port + "");
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(account, new String(password).toCharArray());
			}
		});
	}

	/**
	 * 描述:注册HTTPS安全策略
	 * 作者:ZhangYi
	 * 时间:2015年11月6日 上午11:15:32
	 * 参数：(参数列表)
	 * 
	 * @param url
	 */
	public static void registerProtocol(String url) throws Exception {
		if (StringUtils.isNotBlank(url) && url.startsWith(PROTOCOL_HTTPS)) {
			try {
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, new TrustManager[] { new X509TrustManager() {
					private X509Certificate[] certificates;

					@Override
					public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
						if (this.certificates == null) {
							this.certificates = certificates;
							logger.info("init at checkClientTrusted");
						}
					}

					@Override
					public void checkServerTrusted(X509Certificate[] ax509certificate, String ss) throws CertificateException {
						if (this.certificates == null) {
							this.certificates = ax509certificate;
							logger.info("init at checkServerTrusted");
						}
					}

					@Override
					public X509Certificate[] getAcceptedIssuers() {
						// TODO Auto-generated method stub
						return null;
					}
				} }, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
					public boolean verify(String ss, SSLSession sslsession) {
						logger.info("WARNING: Host name does not match the certificate subject provided by the peer");
						return true;
					}
				});
				HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
				Protocol https = new Protocol("https", new ProtocolSocketFactory() {
					@Override
					public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
						if (params == null) {
							throw new IllegalArgumentException("Parameters may not be null");
						}
						int timeout = params.getConnectionTimeout();
						SSLSocketFactory socketfactory = HttpsURLConnection.getDefaultSSLSocketFactory();
						if (timeout == 0) {
							return socketfactory.createSocket(host, port, localAddress, localPort);
						} else {
							Socket socket = socketfactory.createSocket();
							SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
							SocketAddress remoteaddr = new InetSocketAddress(host, port);
							socket.bind(localaddr);
							socket.connect(remoteaddr, timeout);
							return socket;
						}
					}

					@Override
					public Socket createSocket(String host, int port, InetAddress localAddress, int localPort) throws IOException, UnknownHostException {
						return HttpsURLConnection.getDefaultSSLSocketFactory().createSocket(host, port, localAddress, localPort);
					}

					@Override
					public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
						return HttpsURLConnection.getDefaultSSLSocketFactory().createSocket(host, port);
					}
				}, 443);
				Protocol.registerProtocol("https", https);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	/**
	 * 描述: 判断服务连通性
	 * 作者: ZhangYi
	 * 时间: 2019年3月20日 下午1:53:53
	 * 参数: (参数列表)
	 * 
	 * @param url  请求URL
	 * @param auth 认证信息(username+":"+password)
	 * @return (true:连接成功,false:连接失败)
	 */
	public static boolean checkConnection(String url, String auth) {
		boolean flag = false;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(5 * 1000);
			if (auth != null && !"".equals(auth)) {
				String authorization = "Basic " + new String(Base64.encodeBase64(auth.getBytes()));
				connection.setRequestProperty("Authorization", authorization);
			}
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				flag = true;
			}
			connection.disconnect();
		} catch (Exception e) {
			logger.error("--Server Connect Error !", e);
		}
		return flag;
	}

	/**
	 * 描述: 判断服务连通性
	 * 作者: ZhangYi
	 * 时间: 2019年3月20日 下午1:53:53
	 * 参数: (参数列表)
	 * 
	 * @param url 请求URL
	 * @return (true:连接成功,false:连接失败)
	 */
	public static boolean ping(String url) {
		boolean flag = false;
		HttpResponse response = null;
		try {
			HttpRequestBase http = new HttpHead(url);
			response = client.execute(http);
			if (response != null && (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK || response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_CREATED)) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("--Server Connect Error !", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	private static String httpParams(String method, Object params) {
		StringBuilder builder = new StringBuilder("");
		if (params == null) {
			return builder.toString();
		}
		if (!(method.equalsIgnoreCase(METHOD_POST) || method.equalsIgnoreCase(METHOD_PUT))) {
			if (params instanceof Map || params instanceof JSONObject) {
				Map<Object, Object> map = (Map<Object, Object>) params;
				for (Entry<Object, Object> entity : map.entrySet()) {
					Object key = entity.getKey();
					if (key == null || StringUtils.isBlank(key.toString())) {
						continue;
					}
					Object value = entity.getValue();
					if (StringUtils.isNotBlank(builder.toString())) {
						builder.append("&");
					}
					builder.append(key + "=" + value);
				}
			} else if (params instanceof Collection) {
				Collection<Object> objs = ((Collection<Object>) params);
				Object obj = objs.stream().filter(Objects::nonNull).findAny().orElse(Collections.emptyMap());
				if (obj instanceof Map || obj instanceof JSONObject) {
					for (Object object : objs) {
						Map<Object, Object> map = (Map<Object, Object>) object;
						for (Entry<Object, Object> entity : map.entrySet()) {
							Object key = entity.getKey();
							if (key == null || StringUtils.isBlank(key.toString())) {
								continue;
							}
							Object value = entity.getValue();
							if (StringUtils.isNotBlank(builder.toString())) {
								builder.append("&");
							}
							builder.append(key + "=" + value);
						}
					}
				} else {
					builder.append(JSON.toJSONString(params));
				}
			} else {
				builder.append(params);
			}
		} else {
			builder.append(JSON.toJSONString(params));
		}
		return builder.toString();
	}

	/**
	 * @param url    请求URL
	 * @param method 请求URL
	 * @param param  json参数(post|put)
	 * @param auth   认证信息(username+":"+password)
	 * @return 返回结果
	 */
	public static String httpRequest(String url, String method, Map<String, Object> params, String auth) {
		String result = httpRequest(url, method, params, null, auth);
		return result;
	}

	/**
	 * 描述: Http请求封装
	 * 
	 * @author ZhangYi
	 * @date 2019-05-27 11:18:40
	 * @param url     请求URL
	 * @param method  请求方式
	 * @param params  请求参数
	 * @param headers 请求头
	 * @param auth    认证信息(username+":"+password)
	 * @return
	 */
	public static String httpRequest(String url, String method, Map<String, Object> params, Map<String, String> headers, String auth) {
		String result = httpRequest(url, method, params, headers, null, null, auth);
		return result;
	}

	/**
	 * 描述: Http请求封装
	 * 
	 * @author ZhangYi
	 * @date 2019-05-27 11:18:40
	 * @param url     请求URL
	 * @param method  请求方式
	 * @param params  请求参数
	 * @param headers 请求头
	 * @param auth    认证信息(username+":"+password)
	 * @return
	 */
	public static <T> String httpRequest(String url, String method, Collection<T> params, Map<String, String> headers, String auth) {
		String result = httpRequest(url, method, params, headers, null, null, auth);
		return result;
	}

	/**
	 * 描述: Http请求封装
	 * 
	 * @author ZhangYi
	 * @date 2019-05-27 11:18:40
	 * @param url         请求URL
	 * @param method      请求方式
	 * @param params      请求参数
	 * @param headers     请求头
	 * @param contentType 内容类型
	 * @param charset     编码方式
	 * @param auth        认证信息(username+":"+password)
	 * @return
	 */
	public static String httpRequest(String url, String method, Object params, Map<String, String> headers, String contentType, String charset, String auth) {
		String result = null;
		HttpResponse response = null;
		try {
			Charset encode = Consts.UTF_8;
			if (!StringUtils.isBlank(charset) && Charset.isSupported(charset)) {
				encode = Charset.forName(charset);
			}
			if (StringUtils.isNotBlank(contentType)) {
				if (contentType.contains("\"") || contentType.contains(";") || contentType.contains(",")) {
					int fromIndex = contentType.length();
					if (contentType.contains("\"")) {
						fromIndex = contentType.indexOf('"');
					} else if (contentType.contains(",")) {
						fromIndex = contentType.indexOf(',');
					} else {
						fromIndex = contentType.indexOf(';');
					}
					contentType = contentType.substring(0, contentType.indexOf(fromIndex));
				}
			} else {
				contentType = ContentType.APPLICATION_JSON.getMimeType();
			}
			ContentType httpContentType = ContentType.create(contentType, encode);
			String param = httpParams(method, params);
			if (!(method.equalsIgnoreCase(METHOD_POST) || method.equalsIgnoreCase(METHOD_PUT))) {
				url += (url.contains("?") ? (url.endsWith("&") ? "" : "&") : "?") + param;
			}
			HttpRequestBase http = new HttpGet(url);
			if (method.equalsIgnoreCase(METHOD_POST)) {
				http = new HttpPost(url);
				StringEntity body = new StringEntity(param, httpContentType);
				body.setContentType(httpContentType.getMimeType());
				((HttpPost) http).setEntity(body);
			} else if (method.equalsIgnoreCase(METHOD_PUT)) {
				http = new HttpPut(url);
				StringEntity body = new StringEntity(param, httpContentType);
				body.setContentType(httpContentType.getMimeType());
				((HttpPut) http).setEntity(body);
			} else if (method.equalsIgnoreCase(METHOD_DELETE)) {
				http = new HttpDelete(url);
			} else if (method.equalsIgnoreCase(METHOD_HEAD)) {
				http = new HttpHead(url);
			} else if (method.equalsIgnoreCase(METHOD_OPTIONS)) {
				http = new HttpOptions(url);
			} else if (method.equalsIgnoreCase(METHOD_TRACE)) {
				http = new HttpTrace(url);
			}
			if (StringUtils.isNotBlank(auth)) {
				String authorization = "Basic " + new String(Base64.encodeBase64(auth.getBytes()));
				http.setHeader(HttpHeaders.AUTHORIZATION, authorization);
			}
			http.setHeader(HttpHeaders.CONNECTION, "close");
			if (headers != null && !headers.isEmpty()) {
				for (Entry<String, String> entity : headers.entrySet()) {
					String key = entity.getKey();
					if (StringUtils.isBlank(key)) {
						continue;
					}
					String value = entity.getValue();
					http.setHeader(key, value);
				}
			}
			response = client.execute(http);
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK || response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, Consts.UTF_8);
			} else {
				result = "{\"statuscode\":" + response.getStatusLine().getStatusCode() + ",\"message\":\"" + response.getStatusLine().getReasonPhrase() + "\"}";
				result = JSON.parseObject(result).toJSONString();
			}
		} catch (Exception e) {
			logger.error("--http request error !", e);
			result = e.getMessage();
		} finally {
			HttpClientUtils.closeQuietly(response);
		}
		return result;
	}

	/**
	 * @param url    请求URL
	 * @param method 请求URL
	 * @param param  json参数(post|put)
	 * @param auth   认证(username+:+password)
	 * @return 返回结果
	 */
	public static String urlRequest(String url, String method, String param, String auth) {
		return urlRequest(url, method, param, null, auth);
	}

	/**
	 * @param url    请求URL
	 * @param method 请求URL
	 * @param param  json参数(post|put)
	 * @param auth   认证(username+:+password)
	 * @return 返回结果
	 */
	public static String urlRequest(String url, String method, String param, Map<String, String> headers, String auth) {
		String result = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(60 * 1000);
			connection.setRequestMethod(method.toUpperCase());
			if (auth != null && !"".equals(auth)) {
				String authorization = "Basic " + new String(Base64.encodeBase64(auth.getBytes()));
				connection.setRequestProperty(HttpHeaders.AUTHORIZATION, authorization);
			}
			String contentType = null;
			String charset = null;
			if (headers != null && !headers.isEmpty()) {
				for (Entry<String, String> entity : headers.entrySet()) {
					String key = entity.getKey();
					if (StringUtils.isBlank(key)) {
						continue;
					}
					String value = entity.getValue();
					if (key.equalsIgnoreCase(HttpHeaders.CONTENT_TYPE) || key.equalsIgnoreCase("contentType")) {
						contentType = value;
						continue;
					}
					if (key.equalsIgnoreCase(HttpHeaders.CONTENT_ENCODING) || key.equalsIgnoreCase("contentEncoding")) {
						charset = value;
						continue;
					}
					connection.setRequestProperty(key, value);
				}
			}
			if (StringUtils.isNotBlank(contentType)) {
				connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, contentType);
			}
			if (StringUtils.isNotBlank(charset) && Charset.isSupported(charset)) {
				connection.setRequestProperty(HttpHeaders.CONTENT_ENCODING, charset);
			}
			connection.setRequestProperty(HttpHeaders.CONNECTION, "close");
			if (param != null && !"".equals(param)) {
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.connect();
				DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
				dos.write(param.getBytes(Consts.UTF_8));
				dos.flush();
				dos.close();
			} else {
				connection.connect();
			}
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK || connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
				InputStream in = connection.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buff = new byte[1024];
				int len = 0;
				while ((len = in.read(buff, 0, buff.length)) > 0) {
					out.write(buff, 0, len);
				}
				byte[] data = out.toByteArray();
				in.close();
				result = data != null && data.length > 0 ? new String(data, Consts.UTF_8) : null;
			} else {
				result = "{\"statuscode\":" + connection.getResponseCode() + ",\"message\":\"" + connection.getResponseMessage() + "\"}";
				result = JSON.parseObject(result).toJSONString();
			}
			connection.disconnect();
		} catch (Exception e) {
			logger.error("--http request error !", e);
		}
		return result;
	}

	/**
	 * 描述: HTTP|HTTPS下载文件
	 * 作者: ZhangYi
	 * 时间: 2019年3月19日 上午10:36:14
	 * 参数: (参数列表)
	 * 
	 * @param url    请求URL
	 * @param method 请求URL
	 * @param param  json参数(post|put)
	 * @param auth   认证(username+:+password)
	 */
	public static InputStream download(String url, String method, String param, String auth) {
		InputStream in = null;
		try {
			String charset = Consts.UTF_8.name();
			if (matchChinese(url)) {
				charset = "GBK";
			}

			HttpRequestBase http = new HttpGet(url);
			if (method.equalsIgnoreCase(METHOD_POST)) {
				http = new HttpPost(url);
				StringEntity body = new StringEntity(param, ContentType.APPLICATION_JSON);
				body.setContentType(ContentType.APPLICATION_JSON.getMimeType());
				body.setContentEncoding(charset);
				((HttpPost) http).setEntity(body);
			}
			http.setHeader(HTTP.CONTENT_ENCODING, charset);
			if (StringUtils.isNotBlank(auth)) {
				String authorization = "Basic " + new String(Base64.encodeBase64(auth.getBytes()));
				http.setHeader(HttpHeaders.AUTHORIZATION, authorization);
			}
			http.setHeader(HttpHeaders.CONNECTION, "close");
			CloseableHttpResponse resp = client.execute(http);
			HttpEntity entity = resp.getEntity();
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK || resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_CREATED) {
				in = entity.getContent();
			} else {
				String message = EntityUtils.toString(entity, Consts.UTF_8);
//				String result = "{\"statuscode\":"+resp.getStatusLine().getStatusCode()+",\"message\":\""+message+"\"}";
//				in = new ByteArrayInputStream(JSON.parseObject(result).toJSONString().getBytes());
				logger.error("HTTP|HTTPS download error:{}", message);
			}
		} catch (IOException e) {
			logger.error("HTTP|HTTPS download error", e);
		}
		return in;
	}

	/**
	 * <pre>
	 * 描述: URL编码
	 * &#64;author yi.zhang
	 * 时间: 2017年9月15日 下午3:33:38
	 * &#64;param target 目标字符串
	 * &#64;return
	 * </pre>
	 */
	public static String encode(String target) {
		String result = target;
		try {
			result = URLEncoder.encode(target, Consts.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			logger.error("--http encode error !", e);
		}
		return result;
	}

	/**
	 * <pre>
	 * 描述: URL解码
	 * &#64;author yi.zhang
	 * 时间: 2017年9月15日 下午3:33:38
	 * &#64;param target 目标字符串
	 * &#64;return
	 * </pre>
	 */
	public static String decode(String target) {
		String result = target;
		try {
			result = URLDecoder.decode(target, Consts.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			logger.error("--http decode error !", e);
		}
		return result;
	}

	public static final Pattern pattern = Pattern.compile("[^\\x00-\\xff]");

	/**
	 * 描述: 匹配双字节字符（汉字、中文标点符号等）
	 * 作者: ZhangYi
	 * 时间: 2019年3月19日 下午2:22:50
	 * 参数: (参数列表)
	 * 
	 * @param target
	 * @return
	 */
	public static boolean matchChinese(String target) {
		if (StringUtils.isEmpty(target)) {
			return false;
		}
		return pattern.matcher(target).find();
	}

	/**
	 * 描述: 获取本地IP
	 * 
	 * @author ZhangYi
	 * @date 2019-06-20 14:43:34
	 * @return
	 * @throws SocketException
	 */
	public static String getLocalHost() throws SocketException {
		String local = "127.0.0.1";
		Map<String, NetworkInterface> networks = networks();
		for (Map.Entry<String, NetworkInterface> entry : networks.entrySet()) {
			String host = entry.getKey();
			NetworkInterface network = entry.getValue();
			System.out.println("host:" + host + ",name:" + network.getName() + ",displayName:" + network.getDisplayName() + ",Loopback:" + network.isLoopback() + ",PointToPoint:" + network.isPointToPoint() + ",Up:" + network.isUp() + ",Virtual:" + network.isVirtual() + ",Index:" + network.getIndex() + ",MTU:" + network.getMTU());
			if (network.isLoopback() || network.isVirtual()) {
				continue;
			}
			String[] splits = host.split("\\.");
			if (splits.length != 4) {
				continue;
			}
			if (!(host.startsWith("192.168") || host.startsWith("172.") && Integer.valueOf(splits[1]) >= 16 && Integer.valueOf(splits[1]) <= 31 || host.startsWith("10.") && Integer.valueOf(splits[1]) >= 0 && Integer.valueOf(splits[1]) <= 255)) {
				continue;
			}
			if (host.endsWith(".0") || host.endsWith(".1") || host.endsWith(".255") || host.endsWith(".254")) {
				continue;
			}
			if (StringUtils.isNotBlank(network.getDisplayName()) && network.getDisplayName().toLowerCase().contains("loopback")) {
				continue;
			}
			return host;
		}
		return local;
	}

	/**
	 * 
	 * 描述: 获取主机所有地址对应网卡信息
	 * 
	 * @author ZhangYi
	 * @date 2019-06-20 14:44:07
	 * @return
	 */
	public static Map<String, NetworkInterface> networks() {
		Map<String, NetworkInterface> networks = Maps.newHashMap();
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			NetworkInterface networkInterface;
			Enumeration<InetAddress> inetAddresses;
			InetAddress inetAddress;
			String ip;
			while (networkInterfaces.hasMoreElements()) {
				networkInterface = networkInterfaces.nextElement();
				inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					inetAddress = inetAddresses.nextElement();
					if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
						ip = inetAddress.getHostAddress();
						networks.put(ip, networkInterface);
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return networks;
	}

	public static void main(String[] args) {
		String id = "";
//		String url = "http://172.21.32.31:8172/hollysys-eam/realtime/read-tags";
		String url = "http://172.21.32.31:8172/hollysys-eam/realtime/write-tags";
		if (!"".equals(id)) {
			url = url + "/" + id;
		} else {
//			url=url+"/_search";
		}

		String method = "get";
//		String body = "[\"dbs-test#cd1\",\"dbs-test#cd3\"]";
		String body = "[\r\n" + "  {\r\n" + "    \"tag\": \"A_3\",\r\n" + "    \"type\": 0,\r\n" + "    \"value\": \"1\"\r\n" + "  }\r\n" + "]";
//		String body = "{\"name\":\"mobile music\",\"operator\":\"10000\",\"content\":\"I like music!\",\"createTime\":\"2017-04-20\"}";
		String result = null;
//		String auth="elastic:elastic";
		result = ping(url) + "";
//		result = checkConnection("http://127.0.0.1:9200",null)+"";
//		System.out.println(result);
		List<JSONObject> params = JSON.parseArray(body, JSONObject.class);
		result = httpRequest(url, method, params, null, null);
		System.out.println("---------------------------------------------------------");
//		result = urlRequest(url, method, param);
		System.out.println(result);
	}
}
