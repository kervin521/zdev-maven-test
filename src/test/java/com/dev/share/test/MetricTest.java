package com.dev.share.test;

import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.CachedThreadStatesGaugeSet;
import com.codahale.metrics.jvm.ClassLoadingGaugeSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.dev.share.metrics.MetricsHandler;

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
	public static void jvm() {
		MetricRegistry metrics = MetricsHandler.registry();
		metrics.register("jvm.mem", new MemoryUsageGaugeSet());
		metrics.register("jvm.gc", new GarbageCollectorMetricSet());
		metrics.register("jvm.thread", new ThreadStatesGaugeSet());
		metrics.register("jvm.class.load", new ClassLoadingGaugeSet());
		metrics.register("jvm.cache", new CachedThreadStatesGaugeSet(1,TimeUnit.MILLISECONDS));
		metrics.register("jvm.buffer.pool", new BufferPoolMetricSet(org.mockito.Mockito.mock(MBeanServer.class)));
	}
	public static void main(String[] args) {
		console.start(1, TimeUnit.SECONDS);
		jvm();
		test();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				console.close();
			}
		});
	}

}
