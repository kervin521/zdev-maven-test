package com.dev.share.test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Hex;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.dev.share.ThreadPool.ScheduleRunnable;
import com.dev.share.metrics.MetricsHandler;
import com.dev.share.util.CipherUtils;
import com.dev.share.util.StringUtils;

public class CipherTest {
	public static int core = Runtime.getRuntime().availableProcessors();
	public static ExecutorService shedule = Executors.newScheduledThreadPool(core*2);
//	public static Meter HMAC = MetricsHandler.meter("HMAC");
	public static Meter AES = MetricsHandler.meter("AES");
//	public static Meter RSA = MetricsHandler.meter("RSA");
//	public static Meter ECC = MetricsHandler.meter("ECC");
//	public static Meter MD5 = MetricsHandler.meter("MD5");
	public static void mutiPool(int size) {
		ConsoleReporter console = MetricsHandler.console();
		console.start(1, TimeUnit.MILLISECONDS);
		for(int i=0;i<size;i++) {
			shedule.execute(new ScheduleRunnable(i) {
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					final int worker = this.getWorker();
					while(true) {
						String target = "2018.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
						String result = null;
						try {
//							result = CipherUtils.md5(target);
//							MD5.mark();
//							result = CipherUtils.hmac(target);
//							HMAC.mark();
							result = CipherUtils.encryptAES(target);
							AES.mark();
//							result = CipherUtils.encryptRSA(target);
//							RSA.mark();
//							result = CipherUtils.encryptECC(target);
//							ECC.mark();
						} catch (Exception e) {
							e.printStackTrace();// 异常信息
						}
						
						long deply = System.currentTimeMillis();
						long diff = Double.valueOf((deply-time)/1000).longValue();
						if(diff%10==0) {
							System.out.println("------------------------------------["+worker+"]{target:"+target+",time:"+StringUtils.time(0, deply)+",result:"+result);
						}
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
		String target = "2019.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
		String hash = CipherUtils.encryptAES(target);
		String result = CipherUtils.decryptAES(hash);
		System.out.println("-----AES数据:"+target);
		System.out.println("-----AES加密:"+hash);
		System.out.println("-----AES解密:"+result);
	}
	public static void testMD5(int size) throws Exception {
		String target = "2019.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
		String hash = CipherUtils.md5(target);
		System.out.println("-----MD5数据:"+target);
		System.out.println("-----MD5加密:"+hash);
	}
	public static void testHMAC(int size) throws Exception {
		String target = "2019.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
//		String key = CipherUtils.generatorHMACKey();
//		System.out.println("-----HMAC公钥:"+key);
		String hash = CipherUtils.hmac(target);
		System.out.println("-----HMAC数据:"+target);
		System.out.println("-----HMAC加密:"+hash);
	}
	public static void main(String[] args) throws Exception {
		int size = 100;
		String hex = Hex.encodeHexString("kjcx".getBytes());
		String hash = new String(Hex.decodeHex(hex.toCharArray()));
		System.out.println("-----hex:"+hex);
		System.out.println("-----hash:"+hash);
//		testRSA(size);
//		testRSA2(size);
//		testECC(size);
//		testAES(size);
//		testMD5(size);
//		testHMAC(size);
		mutiPool(size);
	}
}
