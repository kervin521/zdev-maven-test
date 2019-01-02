package com.dev.share.metrics;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class MD5Digest {
	private MessageDigest md5 = null;
	public MD5Digest(){
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();// 异常信息
		}
	}
	/**
	 * 描述:MD5加密算法[多实例]
	 * 作者:ZhangYi
	 * 时间:2018年12月29日 上午9:04:40
	 * 参数：(参数列表)
	 * @param target	目标内容
	 * @return
	 * </pre>
	 */
	public String digest(String target) {
		md5.reset();
		md5.update(target.getBytes());
		byte[] hash = md5.digest();
		return Hex.encodeHexString(hash);
	}
	/**
	 * 描述:MD5加密算法[静态]
	 * 作者:ZhangYi
	 * 时间:2018年12月29日 上午9:04:40
	 * 参数：(参数列表)
	 * @param target	目标内容
	 * @return
	 * </pre>
	 */
	public static String md5(String target) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(target.getBytes());
			byte[] hash = md5.digest();
			return Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 描述:MD5加密算法[同步]
	 * 作者:ZhangYi
	 * 时间:2018年12月29日 上午9:04:40
	 * 参数：(参数列表)
	 * @param target	目标内容
	 * @return
	 * </pre>
	 */
	public static synchronized String synmd5(String target) {
		try {
			MessageDigest synmd5 = MessageDigest.getInstance("MD5");
			synmd5.reset();
			synmd5.update(target.getBytes());
			byte[] hash = synmd5.digest();
			return Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
