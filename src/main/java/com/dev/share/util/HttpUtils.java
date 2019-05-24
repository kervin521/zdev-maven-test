package com.hollysys.smart.factory.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
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
	public final static String PROTOCOL_HTTP="http://";
	/**
	 * HTTPS协议:https://
	 */
	public final static String PROTOCOL_HTTPS="https://";
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
	 * @param port		代理端口
	 * @param account	认证账号
	 * @param password	认证密码
	 */
	public static void auth(String proxyHost,int port,final String account,final String password){
		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", port+"");
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(account, new String(password).toCharArray());
			}
		});
	}
	/**
	  * 描述: 判断服务连通性
	  * 作者: ZhangYi
	  * 时间: 2019年3月20日 下午1:53:53
	  * 参数: (参数列表)
	 * @param url	请求URL
	 * @param auth	认证信息(username+":"+password)
	 * @return (true:连接成功,false:连接失败)
	 */
	public static boolean checkConnection(String url,String auth){
		boolean flag = false;
		try {
			HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
			connection.setConnectTimeout(5*1000);
			if(auth!=null&&!"".equals(auth)){
				String authorization = "Basic "+new String(Base64.encodeBase64(auth.getBytes()));
				connection.setRequestProperty("Authorization", authorization);
			}
			connection.connect();
			if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
				flag = true;
			}
			connection.disconnect();
		}catch (Exception e) {
			logger.error("--Server Connect Error !",e);
		}
		return flag;
	}
	/**
	  * 描述: 判断服务连通性
	  * 作者: ZhangYi
	  * 时间: 2019年3月20日 下午1:53:53
	  * 参数: (参数列表)
	 * @param url	请求URL
	 * @return (true:连接成功,false:连接失败)
	 */
	public static boolean ping(String url){
		boolean flag = false;
		HttpResponse response = null;
		try {
			HttpRequestBase http = new HttpHead(url);
			response = client.execute(http);
			if(response!=null&&(response.getStatusLine().getStatusCode()==HttpURLConnection.HTTP_OK||response.getStatusLine().getStatusCode()==HttpURLConnection.HTTP_CREATED)){
				flag = true;
			}
		}catch (Exception e) {
			logger.error("--Server Connect Error !",e);
		}finally {
			HttpClientUtils.closeQuietly(response);
		}
		return flag;
	}
	/**
	 * @param url 请求URL
	 * @param method 请求URL
	 * @param param	json参数(post|put)
	 * @param auth	认证信息(username+":"+password)
	 * @return 返回结果
	 */
	public static String httpRequest(String url,String method,Map<String,Object> params,String auth){
		String result = httpRequest(url, method, params,null, auth);
		return result;
	}
	/**
	 * @param url 请求URL
	 * @param method 请求URL
	 * @param param	json参数(post|put)
	 * @param auth	认证信息(username+":"+password)
	 * @return 返回结果
	 */
	public static String httpRequest(String url,String method,List<Object> params,Map<String,String> headers,String auth){
		String result = null;
		HttpResponse response = null;
		try {
			String contentType = "application/json";
			String param = "";
			if(params!=null&&!params.isEmpty()) {
				Object obj = params.stream().filter(Objects::nonNull).findAny();
				if(!(method.equalsIgnoreCase(METHOD_POST)||method.equalsIgnoreCase(METHOD_PUT))&&obj instanceof Map) {
					for (Object object : params) {
						Map<Object,Object> objs = (Map<Object,Object>)object;
						for(Entry<Object, Object> entity:objs.entrySet()) {
							Object key = entity.getKey();
							if(key instanceof String &&StringUtils.isBlank((String)key)) {
								continue;
							}
							Object value = entity.getValue();
							if(!"".equals(param)) {
								param += "&";
							}
							param += key+"="+value;
						}
					}
					url += (url.contains("?")?(url.endsWith("&")?"":"&"):"?")+param;
				}else {
					param = JSON.toJSONString(params);
				}
			}
			HttpRequestBase http = new HttpGet(url);
			if(method.equalsIgnoreCase(METHOD_POST)){
				http = new HttpPost(url);
				StringEntity body = new StringEntity(param,ContentType.APPLICATION_JSON);
				body.setContentType(contentType);
				((HttpPost)http).setEntity(body);
			}else if(method.equalsIgnoreCase(METHOD_PUT)){
				http = new HttpPut(url);
				StringEntity body = new StringEntity(param,ContentType.APPLICATION_JSON);
				body.setContentType(contentType);
				((HttpPut)http).setEntity(body);
			}else if(method.equalsIgnoreCase(METHOD_DELETE)){
				http = new HttpDelete(url);
			}else if(method.equalsIgnoreCase(METHOD_HEAD)){
				http = new HttpHead(url);
			}else if(method.equalsIgnoreCase(METHOD_OPTIONS)){
				http = new HttpOptions(url);
			}else if(method.equalsIgnoreCase(METHOD_TRACE)){
				http = new HttpTrace(url);
			}
			if(auth!=null&&!"".equals(auth)){
				String authorization = "Basic "+new String(Base64.encodeBase64(auth.getBytes()));
				http.setHeader("Authorization", authorization);
			}
			http.setHeader("Connection", "close");
			if(headers!=null&&!headers.isEmpty()) {
				for(Entry<String, String> entity:headers.entrySet()) {
					String key = entity.getKey();
					if(StringUtils.isBlank(key)) {
						continue;
					}
					String value = entity.getValue();
					http.setHeader(key, value);
				}
			}
			response = client.execute(http);
			if(response.getStatusLine().getStatusCode()==HttpURLConnection.HTTP_OK||response.getStatusLine().getStatusCode()==HttpURLConnection.HTTP_CREATED){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity,Consts.UTF_8);
			}else {
				result = "{\"statuscode\":"+response.getStatusLine().getStatusCode()+",\"message\":\""+response.getStatusLine().getReasonPhrase()+"\"}";
				result = JSON.parseObject(result).toJSONString();
			}
		}catch (Exception e) {
			logger.error("--http request error !",e);
			result = e.getMessage();
		}finally {
			HttpClientUtils.closeQuietly(response);
		}
		return result;
	}
	/**
	 * @param url 请求URL
	 * @param method 请求URL
	 * @param param	json参数(post|put)
	 * @param auth	认证信息(username+":"+password)
	 * @return 返回结果
	 */
	public static String httpRequest(String url,String method,Map<String,Object> params,Map<String,String> headers,String auth){
		String result = null;
		HttpResponse response = null;
		try {
			String param = "";
			if(params!=null&&!params.isEmpty()) {
				if(!(method.equalsIgnoreCase(METHOD_POST)||method.equalsIgnoreCase(METHOD_PUT))) {
					for(Entry<String, Object> entity:params.entrySet()) {
						String key = entity.getKey();
						if(StringUtils.isBlank(key)) {
							continue;
						}
						Object value = entity.getValue();
						if(!"".equals(param)) {
							param += "&";
						}
						param += key+"="+value;
					}
					url += (url.contains("?")?(url.endsWith("&")?"":"&"):"?")+param;
				}else {
					param = JSON.toJSONString(params);
				}
			}
			HttpRequestBase http = new HttpGet(url);
			if(method.equalsIgnoreCase(METHOD_POST)){
				http = new HttpPost(url);
				StringEntity body = new StringEntity(param,ContentType.APPLICATION_JSON);
				body.setContentType("application/json");
				((HttpPost)http).setEntity(body);
			}else if(method.equalsIgnoreCase(METHOD_PUT)){
				http = new HttpPut(url);
				StringEntity body = new StringEntity(param,ContentType.APPLICATION_JSON);
				body.setContentType("application/json");
				((HttpPut)http).setEntity(body);
			}else if(method.equalsIgnoreCase(METHOD_DELETE)){
				http = new HttpDelete(url);
			}else if(method.equalsIgnoreCase(METHOD_HEAD)){
				http = new HttpHead(url);
			}else if(method.equalsIgnoreCase(METHOD_OPTIONS)){
				http = new HttpOptions(url);
			}else if(method.equalsIgnoreCase(METHOD_TRACE)){
				http = new HttpTrace(url);
			}
			if(auth!=null&&!"".equals(auth)){
				String authorization = "Basic "+new String(Base64.encodeBase64(auth.getBytes()));
				http.setHeader(HttpHeaders.AUTHORIZATION, authorization);
			}
			http.setHeader(HttpHeaders.CONNECTION, "close");
			if(headers!=null&&!headers.isEmpty()) {
				for(Entry<String, String> entity:headers.entrySet()) {
					String key = entity.getKey();
					if(StringUtils.isBlank(key)) {
						continue;
					}
					String value = entity.getValue();
					http.setHeader(key, value);
				}
			}
			response = client.execute(http);
			if(response.getStatusLine().getStatusCode()==HttpURLConnection.HTTP_OK||response.getStatusLine().getStatusCode()==HttpURLConnection.HTTP_CREATED){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity,Consts.UTF_8);
			}else {
				result = "{\"statuscode\":"+response.getStatusLine().getStatusCode()+",\"message\":\""+response.getStatusLine().getReasonPhrase()+"\"}";
				result = JSON.parseObject(result).toJSONString();
			}
		}catch (Exception e) {
			logger.error("--http request error !",e);
			result = e.getMessage();
		}finally {
			HttpClientUtils.closeQuietly(response);
		}
		return result;
	}
	/**
	 * @param url 请求URL
	 * @param method 请求URL
	 * @param param	json参数(post|put)
	 * @param auth	认证(username+:+password)
	 * @return 返回结果
	 */
	public static String urlRequest(String url,String method,String param,String auth){
		return urlRequest(url, method, param, null, auth);
	}
	/**
	 * @param url 请求URL
	 * @param method 请求URL
	 * @param param	json参数(post|put)
	 * @param auth	认证(username+:+password)
	 * @return 返回结果
	 */
	public static String urlRequest(String url,String method,String param,String contentType,String auth){
		String result = null;
		try {
			HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
			connection.setConnectTimeout(60*1000);
			connection.setRequestMethod(method.toUpperCase());
			if(auth!=null&&!"".equals(auth)){
				String authorization = "Basic "+new String(Base64.encodeBase64(auth.getBytes()));
				connection.setRequestProperty(HttpHeaders.AUTHORIZATION, authorization);
			}
			if(StringUtils.isNotBlank(contentType)) {
				connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, contentType);
			}
			connection.setRequestProperty(HttpHeaders.CONNECTION, "close");
			if(param!=null&&!"".equals(param)){
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.connect();
				DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
				dos.write(param.getBytes(Consts.UTF_8));
				dos.flush();
				dos.close();
			}else{
				connection.connect();
			}
			if(connection.getResponseCode()==HttpURLConnection.HTTP_OK||connection.getResponseCode()==HttpURLConnection.HTTP_CREATED){
				InputStream in = connection.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buff = new byte[1024];
				int len = 0;
				while((len=in.read(buff, 0, buff.length))>0){
					out.write(buff, 0, len);
				}
				byte[] data = out.toByteArray();
				in.close();
				result = data!=null&&data.length>0?new String(data, Consts.UTF_8):null;
			}else{
				result = "{\"statuscode\":"+connection.getResponseCode()+",\"message\":\""+connection.getResponseMessage()+"\"}";
				result = JSON.parseObject(result).toJSONString();
			}
			connection.disconnect();
		}catch (Exception e) {
			logger.error("--http request error !",e);
		}
		return result;
	}
	/**
	 * 描述: HTTP|HTTPS下载文件
	 * 作者: ZhangYi
	 * 时间: 2019年3月19日 上午10:36:14
	 * 参数: (参数列表)
	 * @param url 请求URL
	 * @param method 请求URL
	 * @param param	json参数(post|put)
	 * @param auth	认证(username+:+password)
	 */
	public static InputStream download(String url,String method,String param,String auth) {
		InputStream in = null;
		try {
			String charset = Consts.UTF_8.name();
			if(matchChinese(url)) {
				charset = "GBK";
			}
			
			HttpRequestBase http = new HttpGet(url);
			if(method.equalsIgnoreCase(METHOD_POST)){
				http = new HttpPost(url);
				StringEntity body = new StringEntity(param,ContentType.APPLICATION_JSON);
				body.setContentType("application/json");
				body.setContentEncoding(charset);
				((HttpPost)http).setEntity(body);
			}
			http.setHeader(HTTP.CONTENT_ENCODING, charset);
			if(auth!=null&&!"".equals(auth)){
				String authorization = "Basic "+new String(Base64.encodeBase64(auth.getBytes()));
				http.setHeader("Authorization", authorization);
			}
			http.setHeader("Connection", "close");
			CloseableHttpResponse resp = client.execute(http);
			HttpEntity entity = resp.getEntity();
			if(resp.getStatusLine().getStatusCode()==HttpURLConnection.HTTP_OK||resp.getStatusLine().getStatusCode()==HttpURLConnection.HTTP_CREATED){
				in = entity.getContent();
			}else{
				String message = EntityUtils.toString(entity,Consts.UTF_8);
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
	 * @author yi.zhang
	 * 时间: 2017年9月15日 下午3:33:38
	 * @param target 目标字符串
	 * @return 
	 * </pre>
	 */
	public static String encode(String target){
		String result = target;
		try {
			result = URLEncoder.encode(target, Consts.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			logger.error("--http encode error !",e);
		}
		return result;
	}
	/**
	 * <pre>
	 * 描述: URL解码
	 * @author yi.zhang
	 * 时间: 2017年9月15日 下午3:33:38
	 * @param target 目标字符串
	 * @return 
	 * </pre>
	 */
	public static String decode(String target){
		String result = target;
		try {
			result = URLDecoder.decode(target, Consts.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			logger.error("--http decode error !",e);
		}
		return result;
	}
	public static final Pattern pattern = Pattern.compile("[^\\x00-\\xff]");
	/**
	  * 描述: 匹配双字节字符（汉字、中文标点符号等）
	  * 作者: ZhangYi
	  * 时间: 2019年3月19日 下午2:22:50
	  * 参数: (参数列表)
	  * @param target 
	  * @return
	 */
	public static boolean matchChinese(String target) {
		if(StringUtils.isEmpty(target)) {
			return false;
		}
		return pattern.matcher(target).find();
	}
	public static void main(String[] args) {
		String id = "";
		String url = "http://172.21.32.10:8127/api/EPMMOS";
		if(!"".equals(id)){
			url=url+"/"+id;
		}else{
//			url=url+"/_search";
		}
//		String method = "post";
//		String body = "{\"query\":{\"match\":{\"operator\":\"test\"}}}";
//		String body = "{\"name\":\"mobile music\",\"operator\":\"10000\",\"content\":\"I like music!\",\"createTime\":\"2017-04-20\"}";
		String result = null;
//		String auth="elastic:elastic";
		result = ping(url)+"";
//		result = checkConnection("http://127.0.0.1:9200",null)+"";
//		System.out.println(result);
//		result = httpRequest(url, method, null,null);
		System.out.println("---------------------------------------------------------");
//		result = urlRequest(url, method, param);
		System.out.println(result);
	}
}

