package com.dev.share.test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.dev.share.ThreadPool.ScheduleRunnable;
import com.dev.share.metrics.MD5Digest;
import com.dev.share.metrics.MetricsHandler;
import com.dev.share.util.StringUtils;

public class MD5Test {
	public static int core = Runtime.getRuntime().availableProcessors();
	public static ExecutorService shedule = Executors.newScheduledThreadPool(core*2);
	public static Meter metric = MetricsHandler.meter("md5");
	public static void main(String[] args) {
		int size = 100;
		ConsoleReporter console = MetricsHandler.console();
		console.start(1, TimeUnit.MILLISECONDS);
		for(int i=0;i<size;i++) {
			shedule.execute(new ScheduleRunnable(i) {
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					long deply = 0;
					final int worker = this.getWorker();
					while(deply<5*60*1000l) {
						String target = "2018.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
						String result = null;
//						result = MD5Digest.md5(target);
//						result = new MD5Digest().digest(target);
//						result = MD5Digest.synmd5(target);
						metric.mark();
						deply = System.currentTimeMillis()-time;
						System.out.println("------------------------------------["+worker+"]{target:"+target+",time:"+StringUtils.time(0, deply)+",result:"+result);
					}
				}
			});
		}
	}
}
