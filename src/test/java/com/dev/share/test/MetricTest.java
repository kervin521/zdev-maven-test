package com.dev.share.test;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricsHandler;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

public class MetricTest {
	protected static volatile ConsoleReporter console = MetricsHandler.console();
	private static final Timer mtimer = MetricsHandler.timer("Metric-Timer");
	public static void test() {
		while(true) {
			Context ctx = mtimer.time();
			try{
				Thread.sleep(60*1000l);
				System.out.println("______________________________________________________________");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				ctx.close();
			}
		}
	}
	public static void main(String[] args) {
		console.start(1, TimeUnit.SECONDS);
		test();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				console.close();
			}
		});
	}

}
