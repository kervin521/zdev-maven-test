package com.dev.share.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目: SF_Common
 * 描述: FTP文件下载功能
 * 作者: zhangyi183790
 * 时间: 2019年3月19日 上午10:35:50
 * 版本: v1.0
 * JDK: 1.8
 */
public class FTPUtils {
	private static Logger logger = LoggerFactory.getLogger(FTPUtils.class);
	/**
	 * FTP协议:ftp://
	 */
	public final static String PROTOCOL_FTP = "ftp://";
	private static FTPClient client = null;

	/**
	 * 描述: FTP打开连接
	 * 作者: ZhangYi
	 * 时间: 2019年3月19日 上午10:36:14
	 * 参数: (参数列表)
	 * 
	 * @param host     FTP地址
	 * @param port     FTP端口
	 * @param account  FTP账号
	 * @param password FTP密码
	 * @param charset  FTP编码
	 */
	private static void open(String host, int port, String account, String password, String charset) {
		try {
			client = new FTPClient();
			if (port > 0) {
				client.connect(host, port);
			} else {
				client.connect(host);
			}
			if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(account)) {
				client.login(account, password);
			}
			if (!StringUtils.isEmpty(charset)) {
				client.setControlEncoding(charset);// 默认:ISO-8859-1
			}
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			client.setBufferSize(1024 * 1024 * 1024);
			client.setDataTimeout(30 * 1000);
		} catch (SocketException e) {
			logger.error("FTP connect error", e);
		} catch (IOException e) {
			logger.error("FTP io error", e);
		}
	}

	/**
	 * 描述: FTP下载文件
	 * 作者: ZhangYi
	 * 时间: 2019年3月19日 上午10:36:14
	 * 参数: (参数列表)
	 * 
	 * @param filePath 文件路径
	 * @param host     FTP地址
	 * @param port     FTP端口
	 * @param account  FTP账号
	 * @param password FTP密码
	 * @param charset  FTP编码
	 */
	public static OutputStream download(String filePath, String host, int port, String account, String password, String charset) {
		OutputStream out = null;
		try {
			open(host, port, account, password, charset);
			client.enterLocalPassiveMode();
			client.retrieveFile(filePath, out);
			close();
		} catch (IOException e) {
			logger.error("FTP download error", e);
		}
		return out;
	}

	/**
	 * 描述: FTP下载文件
	 * 作者: ZhangYi
	 * 时间: 2019年3月19日 上午10:36:14
	 * 参数: (参数列表)
	 * 
	 * @param filePath 文件路径
	 * @param host     FTP地址
	 * @param charset  FTP编码
	 */
	public static OutputStream download(String filePath, String host, String charset) {
		OutputStream out = null;
		try {
			open(host, 0, null, null, charset);
			client.enterLocalPassiveMode();
			client.retrieveFile(filePath, out);
			close();
		} catch (IOException e) {
			logger.error("FTP download error", e);
		}
		return out;
	}

	/**
	 * 描述: FTP下载文件
	 * 作者: ZhangYi
	 * 时间: 2019年3月19日 上午10:36:14
	 * 参数: (参数列表)
	 * 
	 * @param filePath 文件路径
	 * @param host     FTP地址
	 */
	public static ByteArrayInputStream download(String filePath, String host) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		try {
			String charset = null;
			if (matchChinese(filePath)) {
				charset = "GBK";
			}
			open(host, 0, null, null, charset);
			client.enterLocalPassiveMode();
			client.retrieveFile(filePath, out);
			in = new ByteArrayInputStream(out.toByteArray());
			close();
		} catch (IOException e) {
			logger.error("FTP download error", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("FTP IO error", e);
				}
			}
		}
		return in;
	}

	/**
	 * 描述: FTP关闭连接
	 * 作者: ZhangYi
	 * 时间: 2019年3月19日 上午10:36:14
	 * 参数: (参数列表)
	 */
	private static void close() {
		if (null != client && client.isConnected()) {
			try {
				client.logout();// 退出FTP服务器
			} catch (IOException e) {
				logger.error("FTP logout error", e);
			} finally {
				try {
					client.disconnect();// 关闭FTP服务器的连接
				} catch (IOException e) {
					logger.error("FTP disconnect error", e);
				}
			}
		}
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
//		匹配汉字文字： [\u4e00-\u9fa5]或[\u2E80-\u9FFF]
//		匹配非汉字字符： [^\u4e00-\u9fa5]
//		匹配双字节字符（汉字、中文标点符号等）： [^\x00-\xff]

		return pattern.matcher(target).find();
	}
}
