package com.dev.share.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class CipherUtils {// decrypt encrypt
	public static String RSA_PUBLIC_KEY = "305c300d06092a864886f70d0101010500034b00304802410092a7cd67e2323922e6cd79c57fd211f8683a130cf1e602d2a96596d5ad6046e1b6a9802f5a9e74b10dac0a2237376bd4bb101e511a054d1d0625b034ecf7f47f0203010001";
	public static String RSA_PRIVATE_KEY = "30820153020100300d06092a864886f70d01010105000482013d3082013902010002410092a7cd67e2323922e6cd79c57fd211f8683a130cf1e602d2a96596d5ad6046e1b6a9802f5a9e74b10dac0a2237376bd4bb101e511a054d1d0625b034ecf7f47f02030100010240117c5c6b5476f4850acbd90709547cbc0781552027ff6ababf63d3762ab3ddff814b540b403377b0f5f28c01bce25dbfa0c4608946b5c4be46e8a99f18c140e1022100cb71a641a531771d24a7a0362f4d620900683c6d26a994de3adf3b469b3c6bf7022100b88a9042e190a388d93e02a5d37df82b92afb3d6bd552f6e4f56f387b27fc9b9022078e6d259e2b511c784f805130e6b5b5e641a8e0683da75b8d79dc01946b9d2a302206ed29fb1da7e1f0766d70b86044d6904d9d17461a85008ece8eea1cf430de4d902204c7c10492de5dc7428fdef6fd746ccb8ddbcc5a9e2aa3f71682e08214944a6bd";
	public static String ECC_PUBLIC_KEY = "3059301306072a8648ce3d020106082a8648ce3d03010703420004111edebedfbffaa0528dda7f0d905896f5fae7e9a6f4b8c1f001d4e6fa63e6dbff68684b358ff886dc4ceefc13281da1944ba682a7eec4a98ec8af69dfc2004d";
	public static String ECC_PRIVATE_KEY = "3041020100301306072a8648ce3d020106082a8648ce3d030107042730250201010420865e760c64adc53d37c90706ecb968721e5a72f50686e7a06fcb1305e6a44fe6";
//	public static String HMAC_KEY = "c0fee8bbba08ac6d6ae210529484f24c2d50da66ba4d3986924533c6fcf8e35c7e8cabd50dfae8da6a9e70606e397954962e2f6f7cc57e786b447d16206fcbd2";
//	public static String AES_KEY = "kjcx";
	public static String HMAC_KEY = "6b6a6378";
	public static String AES_KEY = "6b6a6378";
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	/**
	 * @description RSA生成公钥私钥
	 * 作者:ZhangYi
	 * 时间:2019年1月2日 下午3:26:16
	 * 参数：(参数列表)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String[] generatorRSAKey() throws Exception {
		// 1.初始化密钥
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(512);// 密钥长度为64的整数倍>=512，最大是65536
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		String pubKey = Hex.encodeHexString(publicKey.getEncoded());
		String priKey = Hex.encodeHexString(privateKey.getEncoded());
		return new String[] { pubKey, priKey };
	}

	/**
	 * @description ECC生成公钥私钥
	 * 作者:ZhangYi
	 * 时间:2019年1月2日 下午3:26:16
	 * 参数：(参数列表)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String[] generatorECCKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
		keyPairGenerator.initialize(256, new SecureRandom());// 密钥长度为64的整数倍>=512，最大是65536
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
		ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
		String pubKey = Hex.encodeHexString(publicKey.getEncoded());
		String priKey = Hex.encodeHexString(privateKey.getEncoded());
		return new String[] { pubKey, priKey };
	}

	/**
	 * @description HMAC生成秘钥
	 * 作者:ZhangYi
	 * 时间:2019年1月2日 下午3:26:16
	 * 参数：(参数列表)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String generatorHMACKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
		SecretKey secretKey = keyGenerator.generateKey();
		String pubKey = Hex.encodeHexString(secretKey.getEncoded());
		return pubKey;
	}

	/**
	 * 描述:[非对称算法]RSA加密(私钥加密)
	 * 作者:ZhangYi
	 * 时间:2019年1月2日 下午2:21:24
	 * 参数：(参数列表)
	 * 
	 * @param target
	 * @return
	 * @throws Exception
	 *                   </pre>
	 */
	public static String encryptRSA(String target) throws Exception {
		byte[] pk = Hex.decodeHex(RSA_PRIVATE_KEY.toCharArray());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pk);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey key = keyFactory.generatePrivate(keySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] hash = cipher.doFinal(target.getBytes());
		return Hex.encodeHexString(hash);
	}

	/**
	 * 描述:[非对称算法]RSA加密(私钥解密)
	 * 作者:ZhangYi
	 * 时间:2019年1月2日 下午2:21:24
	 * 参数：(参数列表)
	 * 
	 * @param target
	 * @return
	 * @throws Exception
	 *                   </pre>
	 */
	public static String decryptRSA(String target) throws Exception {
		byte[] pk = Hex.decodeHex(RSA_PUBLIC_KEY.toCharArray());
		Cipher cipher = Cipher.getInstance("RSA");
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pk);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey key = keyFactory.generatePublic(keySpec);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] data = Hex.decodeHex(target.toCharArray());
		byte[] hash = cipher.doFinal(data);
		return new String(hash);
	}

	/**
	 * 描述:[非对称算法]RSA加密(公钥加密)
	 * 作者:ZhangYi
	 * 时间:2019年1月2日 下午2:21:24
	 * 参数：(参数列表)
	 * 
	 * @param target
	 * @return
	 * @throws Exception
	 *                   </pre>
	 */
	public static String encryptRSA2(String target) throws Exception {
		byte[] pk = Hex.decodeHex(RSA_PUBLIC_KEY.toCharArray());
		Cipher cipher = Cipher.getInstance("RSA");
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pk);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey key = keyFactory.generatePublic(keySpec);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] hash = cipher.doFinal(target.getBytes());
		return Hex.encodeHexString(hash);
	}

	/**
	 * 描述:[非对称算法]RSA加密(私钥解密)
	 * 作者:ZhangYi
	 * 时间:2019年1月2日 下午2:21:24
	 * 参数：(参数列表)
	 * 
	 * @param target
	 * @return
	 * @throws Exception
	 *                   </pre>
	 */
	public static String decryptRSA2(String target) throws Exception {
		byte[] pk = Hex.decodeHex(RSA_PRIVATE_KEY.toCharArray());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pk);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey key = keyFactory.generatePrivate(keySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] data = Hex.decodeHex(target.toCharArray());
		byte[] hash = cipher.doFinal(data);
		return new String(hash);
	}

	/**
	 * 描述:[非对称算法]ECC加密算法
	 * 作者:ZhangYi
	 * 时间:2016年3月29日 下午3:33:30
	 * 参数：(参数列表)
	 * 
	 * @param target 加密数据
	 * @return
	 * @throws Exception
	 */
	public static String encryptECC(String target) throws Exception {
		byte[] pk = Hex.decodeHex(ECC_PUBLIC_KEY.toCharArray());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pk);
		KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
		PublicKey key = keyFactory.generatePublic(keySpec);
		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] hash = cipher.doFinal(target.getBytes());
		return Hex.encodeHexString(hash);
	}

	/**
	 * 描述:[非对称算法]ECC解密算法
	 * 作者:ZhangYi
	 * 时间:2019年1月2日 下午2:21:24
	 * 参数：(参数列表)
	 * 
	 * @param target 解密数据
	 * @return
	 */
	public static String decryptECC(String target) throws Exception {
		byte[] pk = Hex.decodeHex(ECC_PRIVATE_KEY.toCharArray());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pk);
		KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
		PrivateKey key = keyFactory.generatePrivate(keySpec);
		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] hash = cipher.doFinal(target.getBytes());
		return Hex.encodeHexString(hash);
	}

	/**
	 * 描述:[对称算法]AES加密算法
	 * 作者:ZhangYi
	 * 时间:2016年3月29日 下午3:33:30
	 * 参数：(参数列表)
	 * 
	 * @param target 加密数据
	 * @return
	 * @throws Exception
	 */
	public static String encryptAES(String target) throws Exception {
		byte[] pk = Hex.decodeHex(AES_KEY.toCharArray());
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(pk);
		keyGenerator.init(128, secureRandom);
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES"); // 创建密码器
		byte[] byteContent = target.getBytes("utf-8");
		cipher.init(Cipher.ENCRYPT_MODE, key); // 初始化
		byte[] data = cipher.doFinal(byteContent);
		String encryptResultStr = Hex.encodeHexString(data);
		return encryptResultStr; // 加密
	}

	/**
	 * 描述:[对称算法]AES解密算法
	 * 作者:ZhangYi
	 * 时间:2019年1月2日 下午2:21:24
	 * 参数：(参数列表)
	 * 
	 * @param target 解密数据
	 * @return
	 */
	public static String decryptAES(String target) throws Exception {
		byte[] pk = Hex.decodeHex(AES_KEY.toCharArray());
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(pk);
		keyGenerator.init(128, secureRandom);
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES"); // 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, key); // 初始化
		byte[] data = cipher.doFinal(Hex.decodeHex(target.toCharArray()));
		return new String(data, "utf-8"); // 加密
	}

	/**
	 * 描述:[散列算法]MD5加密算法
	 * 作者:ZhangYi
	 * 时间:2018年12月29日 上午9:04:40
	 * 参数：(参数列表)
	 * 
	 * @param target 目标内容
	 * @return
	 *         </pre>
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
	 * 描述:[散列算法]HMAC加密算法
	 * 作者:ZhangYi
	 * 时间:2018年12月29日 上午9:04:40
	 * 参数：(参数列表)
	 * 
	 * @param target 目标内容
	 * @return
	 *         </pre>
	 */
	public static String hmac(String target) {
		try {
			byte[] pk = Hex.decodeHex(HMAC_KEY.toCharArray());
			// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
			SecretKey secretKey = new SecretKeySpec(pk, "HmacSHA1");
			// 生成一个指定 Mac 算法 的 Mac 对象
			Mac mac = Mac.getInstance("HmacSHA1");
			// 用给定密钥初始化 Mac 对象
			mac.init(secretKey);
			byte[] hash = mac.doFinal(target.getBytes());
			return Hex.encodeHexString(hash);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
