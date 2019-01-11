package com.dev.share.test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.httpclient.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dev.share.ThreadPool.AbstractRunnable;
import com.dev.share.util.StringUtils;

public class ThreadTest {
	private static Logger logger = LoggerFactory.getLogger(ThreadTest.class);
	public static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(4);
	public static void main(String[] args) {
		int size = 100000000;
		for(int i=0;i<size;i++) {
			schedule.execute(new AbstractRunnable(i) {
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					long deply = 0;
					final int worker = this.getWorker();
					Date today = new Date();
					String target = "["+DateUtil.formatDate(today, "yyyy-MM-dd HH:mm:ss")+"]"+today.getTime()+"_"+new Random().nextInt(size);
					String result = null;
					deply = System.currentTimeMillis()-time;
					logger.info("------------------------------------["+worker+"]{target:"+target+",time:"+StringUtils.time(0, deply)+",result:"+result);
					try {
						Thread.sleep(1*1000l);
						System.exit(0);
					} catch (InterruptedException e) {
						e.printStackTrace();// 异常信息
					}
				}
			});
		}
	}
}
