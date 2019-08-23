package com.dev.share.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.httpclient.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dev.share.ThreadPool.AbstractRunnable;
import com.dev.share.ThreadPool.PoolSizeCalculator;
import com.dev.share.spring.SpringContextHolder;
import com.dev.share.util.StringUtils;
import com.hollysys.smartfactory.target.calculate.job.IndexCalculateHandler;

public class ThreadTest {
	private static Logger logger = LoggerFactory.getLogger(ThreadTest.class);
	public static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(4);
	public static String lur(String lura) {
		try {
			Thread.sleep(new Random().nextInt(5)*1000);
			lura="-->"+lura+"_1";
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
		return lura;
	}
	public static void test() {
		int size = 100;
		for (int i = 0; i < size; i++) {
			schedule.execute(new AbstractRunnable(i) {
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					long deply = 0;
					final int worker = this.getWorker();
					Date today = new Date();
					String target = "[" + DateUtil.formatDate(today, "yyyy-MM-dd HH:mm:ss") + "]" + today.getTime() + "_" + new Random().nextInt(size);
					String result = null;
					deply = System.currentTimeMillis() - time;
					logger.info("------------------------------------[" + worker + "]{target:" + target + ",time:" + StringUtils.time(0, deply) + ",result:" + result);
					try {
						Thread.sleep(1 * 1000l);
						System.exit(0);
					} catch (InterruptedException e) {
						e.printStackTrace();// 异常信息
					}
				}
			});
			new Thread(()->{
				
			}).start();
		}
	}
	
	private void estimatePoolSize() {
        PoolSizeCalculator poolSizeCalculator = new PoolSizeCalculator() {
            @Override
            protected BlockingQueue createWorkQueue() {
                // TODO Auto-generated method stub
                return new LinkedBlockingQueue(1000);
            }
            
            @Override
            protected Runnable creatTask() {
                return new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(60*1000L);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                             e.printStackTrace();
                        }
                    }
                };
            }
        };
        poolSizeCalculator.calculateBoundaries(new BigDecimal(1.0), new BigDecimal(100000));
    }
	public static void main(String[] args) {
		int size = 100;
		for (int i = 0; i < size; i++) {
			schedule.execute(new AbstractRunnable(i) {
				@Override
				public void run() {
				    int worker = this.getWorker();
					String result = lur(worker+"");
					System.out.println(worker+""+result);
				}
			});
		}
	}
}
