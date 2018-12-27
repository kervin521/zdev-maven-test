package com.dev.share.test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicTest {

	public static void main(String[] args) throws InterruptedException {

		AtomicLong atomic = new AtomicLong(0);
		while (true) {
			boolean flag = new Random().nextInt(10)%2==0;
			if(flag) {
				atomic.lazySet(0);
			}else {
				atomic.addAndGet(new Random().nextInt(100000));
			}
			Thread.sleep(2000);
			System.out.println(flag+"-------------------------"+atomic.get());
		}

	}

}
