package com.dev.share.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import com.dev.share.ThreadPool.AbstractRunnable;

public class AtomicTest {
	public static int core = Runtime.getRuntime().availableProcessors();
	public static ExecutorService shedule = Executors.newScheduledThreadPool(core * 2);

	public enum MessageID {
		INS;
		// 自增
		private static AtomicLong atomic = new AtomicLong(0);

		public String get(long gatewayId) {// 固定20位:前13位日期+2位网关ID+5位原子计数(范围:1-65535)
			return (new SimpleDateFormat("MMddHHmmssSSS").format(new Date())) + String.format("%02d", gatewayId) + String.format("%05d", (atomic.getAndIncrement() % 0xffff) + 1);
		}

		public String get() {// 固定20位:前13位日期+2位网关ID+5位原子计数(范围:1-65535)
			return ((atomic.getAndIncrement() % 0xffff) + 1) + "";
		}
	}

	public static void main(String[] args) throws InterruptedException {

		AtomicLong atomic = new AtomicLong(0);
//		while (true) {
//			boolean flag = new Random().nextInt(10)%2==0;
//			if(flag) {
//				atomic.lazySet(0);
//			}else {
//				atomic.addAndGet(new Random().nextInt(100000));
//			}
//			Thread.sleep(2000);
//			System.out.println(flag+"-------------------------"+atomic.get());
//		}
//		for(int i=0;i<10;i++) {
//			shedule.execute(new AbstractRunnable(i) {
//				@Override
//				public void run() {
//					System.out.println(this.getWorker()+":"+MessageID.INS.get(0));
//				}
//			});
//					
//		}
//		for(int i=0;i<10;i++) {
//			System.out.println(i+":"+MessageID.INS.get());
//		}
		LongAdder adder = new LongAdder();
		for (int i = 0; i < 10; i++) {
			shedule.execute(new AbstractRunnable(i) {
				@Override
				public void run() {
					adder.increment();
					adder.increment();
					System.out.println(this.getWorker() + ":" + adder.sum());
					if (adder.intValue() == 15) {
						System.out.println(this.getWorker() + "=" + adder.sumThenReset());
					}
				}
			});
		}
	}

}
