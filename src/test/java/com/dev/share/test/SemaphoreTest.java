package com.dev.share.test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(5, true);
		for (int index = 1; index <= 20; index++) {
			final int NO = index;
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						semaphore.acquire();
						double random = new BigDecimal(Math.random()*10).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						System.out.println("Accessing:" + NO);
						Thread.sleep(Double.valueOf(random*1000l).longValue());
						semaphore.release();
						System.out.println("======>sleep time:"+random+" s,available permits:" + semaphore.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			executorService.execute(runnable);
		}
		executorService.shutdown();
	}
}
