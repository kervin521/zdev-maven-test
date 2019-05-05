package com.dev.share.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.dev.share.ThreadPool.AbstractRunnable;
import com.dev.share.metrics.MetricsHandler;
import com.dev.share.util.StringUtils;

public class PhoneTest {
	public static int core = Runtime.getRuntime().availableProcessors();
	public static ExecutorService shedule = Executors.newScheduledThreadPool(core * 2);
	public static Meter metric = MetricsHandler.meter("phone");
	// 移动用户号段
	private static final Integer[] CMCC_P3 = new Integer[] { 134, 135, 136, 137, 138, 139, 141, 147, 150, 151, 152, 157, 158, 159, 172, 178, 182, 183, 184, 187, 188, 198 };
	/**
	 * 移动号码
	 * 134, 135, 136, 137, 138, 139,
	 * 141, 147,
	 * 150, 151, 152, 157, 158, 159,
	 * 172, 178, 1703, 1705, 1706
	 * 182, 183, 184, 187, 188,
	 * 198
	 */
	private static final String CMCC_REG = "^[1]((([3][4-9])|([41,47])|([5][^3-6])|([72,78])|([8](([2-4])|[7,8]))|[98])[0-9]{8})|([70][3,5,6])$";
	// 联通用户号段
	private static final Integer[] UNICOM_P3 = new Integer[] { 130, 131, 132, 140, 145, 146, 155, 156, 166, 167, 171, 175, 176, 185, 186 };
	// 电信用户号段
	private static final Integer[] TELECOM_P3 = new Integer[] { 133, 149, 153, 173, 174, 177, 179, 180, 181, 189, 191, 199 };
	// 移动用户号段
	private static final Integer[] CMCC_P4 = new Integer[] { 1703, 1705, 1706 };
	// 联通用户号段
	private static final Integer[] UNICOM_P4 = new Integer[] { 1704, 1707, 1708, 1709 };
	// 电信用户号段
	private static final Integer[] TELECOM_P4 = new Integer[] { 1700, 1701, 1702 };

	public static void matchReg(List<String> list) {
		for (String phone : list) {
			phone.matches(CMCC_REG);
		}
	}

	public static void match(List<String> list) {
		for (String phone : list) {
			int pre = Integer.valueOf(phone.substring(0, 3));
			boolean flag = true;
			for (Integer s : CMCC_P3) {
				if (s != null && s.equals(pre)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				for (Integer s : CMCC_P4) {
					if (s != null && s.equals(pre)) {
						break;
					}
				}
			}
		}
	}

	public static void main(String[] args) {

//		long start = System.currentTimeMillis();
//		matchReg(list);
//		long end = System.currentTimeMillis();
//		System.out.println("---reg1:"+StringUtils.time(start, end));
//		start = System.currentTimeMillis();
//		match(list);
//		end = System.currentTimeMillis();
//		System.out.println("---match1:"+StringUtils.time(start, end));
		long start = System.currentTimeMillis();

		long end = System.currentTimeMillis();
		System.out.println("---reg2:" + StringUtils.time(start, end));
		start = System.currentTimeMillis();

		end = System.currentTimeMillis();
		System.out.println("---match2:" + StringUtils.time(start, end));
		int size = 100;
		ConsoleReporter console = MetricsHandler.console();
		console.start(1, TimeUnit.MILLISECONDS);
		for (int i = 0; i < size; i++) {
			shedule.execute(new AbstractRunnable(i) {
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					long deply = 0;
					final int worker = this.getWorker();
					while (deply < 5 * 60 * 1000l) {
						List<String> list = new ArrayList<>();
						Random random = new Random();
						int count = 1000;
						for (int i = 0; i < count; i++) {
							int num = 1000000 + i;
							String phone = "1" + random.nextInt(10) + random.nextInt(10) + num + "" + new Random().nextInt(10);
							list.add(phone);
						}
//						match(list);
						matchReg(list);

						metric.mark();
						deply = System.currentTimeMillis() - time;
						System.out.println("------------------------------------[" + worker + "]{time:" + StringUtils.time(0, deply) + "");
					}
				}
			});
		}
	}

}
