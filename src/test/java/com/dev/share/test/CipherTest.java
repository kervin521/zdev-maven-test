package com.dev.share.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Meter;
import com.codahale.metrics.Slf4jReporter;
import com.dev.share.ThreadPool.AbstractRunnable;
import com.dev.share.metrics.MetricsHandler;
import com.dev.share.util.CipherUtils;
import com.dev.share.util.StringUtils;

public class CipherTest {
	private static Logger logger = LoggerFactory.getLogger(CipherTest.class);
	public static int core = Runtime.getRuntime().availableProcessors();
	public static ExecutorService shedule = Executors.newScheduledThreadPool(core*2);
	public static Meter HMAC = MetricsHandler.meter("HMAC");
//	public static Meter AES = MetricsHandler.meter("AES");
//	public static Meter RSA = MetricsHandler.meter("RSA");
//	public static Meter ECC = MetricsHandler.meter("ECC");
//	public static Meter MD5 = MetricsHandler.meter("MD5");
	public static void mutiPool(int size) {
		Slf4jReporter slf4j = MetricsHandler.slf4j();
  		slf4j.start(1, TimeUnit.SECONDS);
		for(int i=0;i<size;i++) {
			shedule.execute(new AbstractRunnable(i) {
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					final int worker = this.getWorker();
					while(true) {
						String target = "【春天的风】来自106，请在30秒内按页面提示提交，若非本人操作，请忽略！";
//						String target = "2018.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
//						List<String> phones = new ArrayList<>();
//						for(int i=1000000;i<1100000;i++) {
//							String phone = "138"+i+""+new Random().nextInt(10);
//							phones.add(phone);
//						}
//						String target = StringUtils.join(phones, ",");
						String result = null;
						long start = System.currentTimeMillis();
						try {
//							result = CipherUtils.md5(target);
//							MD5.mark();
							result = CipherUtils.hmac(target);
							HMAC.mark();
//							result = CipherUtils.encryptAES(target);
//							AES.mark();
//							result = CipherUtils.encryptRSA(target);
//							RSA.mark();
//							result = CipherUtils.encryptECC(target);
//							ECC.mark();
						} catch (Exception e) {
							e.printStackTrace();// 异常信息
						}
						long delay = System.currentTimeMillis();
						logger.info("------------------------------------["+worker+"]{use:"+StringUtils.time(start, delay)+",time:"+StringUtils.time(time, delay)+",result:"+result);
//						long diff = Double.valueOf((delay-time)/1000).longValue();
//						if(diff%10==0) {
//							System.out.println("------------------------------------["+worker+"]{time:"+StringUtils.time(time, delay)+",target:"+result+",result:"+result);
//						}
					}
				}
			});
		}
	}
	public static void testRSA(int size) throws Exception {
		String target = "2019.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
//		String[] keys = CipherUtils.generatorRSAKey();
//		System.out.println("-----RSA公钥:"+keys[0]);
//		System.out.println("-----RSA私钥:"+keys[1]);
		String hash = CipherUtils.encryptRSA(target);
		String result = CipherUtils.decryptRSA(hash);
		System.out.println("-----RSA数据:"+target);
		System.out.println("-----RSA加密:"+hash);
		System.out.println("-----RSA解密:"+result);
	}
	public static void testRSA2(int size) throws Exception {
		String target = "2019.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
		String[] keys = CipherUtils.generatorRSAKey();
		System.out.println("-----RSA2公钥:"+keys[0]);
		System.out.println("-----RSA2私钥:"+keys[1]);
		String hash = CipherUtils.encryptRSA2(target);
		String result = CipherUtils.decryptRSA2(hash);
		System.out.println("-----RSA2数据:"+target);
		System.out.println("-----RSA2加密:"+hash);
		System.out.println("-----RSA2解密:"+result);
	}
	public static void testECC(int size) throws Exception {
		String target = "2019.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
//		String[] keys = CipherUtils.generatorECCKey();
//		System.out.println("-----ECC公钥:"+keys[0]);
//		System.out.println("-----ECC私钥:"+keys[1]);
		String hash = CipherUtils.encryptECC(target);
		String result = CipherUtils.decryptECC(hash);
		System.out.println("-----ECC数据:"+target);
		System.out.println("-----ECC加密:"+hash);
		System.out.println("-----ECC解密:"+result);
	}
	public static void testAES(int size) throws Exception {
//		String target = "2019.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
		long time = System.currentTimeMillis();
		List<String> phones = new ArrayList<>();
		for(int i=1000000;i<1100000;i++) {
			String phone = "138"+i+""+new Random().nextInt(10);
			phones.add(phone);
		}
		long start = System.currentTimeMillis();
		String target = StringUtils.join(phones, ",");
		String hash = CipherUtils.encryptAES(target);
		long delay = System.currentTimeMillis();
		System.out.println("------------------------------------{use:"+StringUtils.time(start, delay)+",time:"+StringUtils.time(time, delay)+",result:"+hash.length());
//		String result = CipherUtils.decryptAES(hash);
//		System.out.println("-----AES数据:"+target);
//		System.out.println("-----AES加密:"+hash);
//		System.out.println("-----AES解密:"+result);
	}
	public static void testMD5(int size) throws Exception {
		String target = "【春天的风】来自106，请在30秒内按页面提示提交，若非本人操作，请忽略！";
//		String target = "2019.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
		String hash = CipherUtils.md5(target);
		System.out.println("-----MD5数据:"+target);
		System.out.println("-----MD5加密:"+hash);
	}
	public static void testHMAC(int size) throws Exception {
		String target = "【春天的风】来自106，请在30秒内按页面提示提交，若非本人操作，请忽略！";
//		String target = "2019.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
//		String key = CipherUtils.generatorHMACKey();
//		System.out.println("-----HMAC公钥:"+key);
		String hash = CipherUtils.hmac(target);
		System.out.println("-----HMAC数据:"+target);
		System.out.println("-----HMAC加密:"+hash);
	}
	public static void main(String[] args) throws Exception {
		int size = 10;
//		String hex = Hex.encodeHexString("kjcx".getBytes());
//		String hash = new String(Hex.decodeHex(hex.toCharArray()));
//		System.out.println("-----hex:"+hex);
//		System.out.println("-----hash:"+hash);
//		testRSA(size);
//		testRSA2(size);
//		testECC(size);
//		testAES(size);
//		testMD5(size);
//		testHMAC(size);
		mutiPool(size);
	}
}
