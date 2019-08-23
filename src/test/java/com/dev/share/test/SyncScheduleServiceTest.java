package com.dev.share.test;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 
 * @project SF_Equipment_Diagnosis
 * @description 设备同步计划
 * 
 * @author ZhangYi
 * @date 2019-04-02 17:04:54
 * @version v1.0
 * @Jdk  1.8
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@EnableAsync
public class SyncScheduleServiceTest {
	private static ThreadLocal<LocalDateTime> local = new ThreadLocal<LocalDateTime>();

//	/**
//	 * @description 诊断信息采集
//	 * 
//	 * @author ZhangYi
//	 * @date 2019-04-02 17:05:26
//	 */
//	@Scheduled(initialDelay = 1 * 1000L, fixedDelay = 5 * 1000L)
//	public void synDiagreports1() {
//		LocalDateTime date = local.get();
//		System.out.println("==1.0=={"+Thread.currentThread().getName()+","+LocalDateTime.now()+","+date+"}");
//		try {
//			// 若轮询间隔(5s)大于内部耽搁时间(2s)，则两轮总共间隔7s,两轮之间间隔5s
//			// 若轮询间隔(2s)小于等于内部耽搁时间(5s)，则两轮总共间隔7s,两轮之间间隔5s
//			Thread.sleep(2*1000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("==1.1=={"+Thread.currentThread().getName()+","+LocalDateTime.now()+","+date+"}");
//		local.set(LocalDateTime.now());
//	}
	/**
	 * @description 诊断信息采集
	 * 
	 * @author ZhangYi
	 * @date 2019-04-02 17:05:26
	 */
	@Scheduled(initialDelay = 1 * 1000L, fixedRate = 2 * 1000L)
	public void synDiagreports2() {
		LocalDateTime date = local.get();
		System.out.println("==2.0=={" + Thread.currentThread().getName() + "," + LocalDateTime.now() + "," + date + "}");
		try {
			// 若轮询间隔(5s)大于内部耽搁时间(2s)，则两轮总共间隔5s,两轮之间间隔3s
			// 若轮询间隔(2s)小于等于内部耽搁时间(5s)，则两轮总共间隔5s,两轮之间间隔0s
			Thread.sleep(5 * 1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("==2.1=={" + Thread.currentThread().getName() + "," + LocalDateTime.now() + "," + date + "}");
		local.set(LocalDateTime.now());
	}
//	/**
//	 * @description 诊断信息采集
//	 * 
//	 * @author ZhangYi
//	 * @date 2019-04-02 17:05:26
//	 */
//	@Scheduled(cron = "0/5 * * * * ?")
//	public void synDiagreports3() {
//		LocalDateTime date = local.get();
//		System.out.println("==3.0=={"+Thread.currentThread().getName()+","+LocalDateTime.now()+","+date+"}");
//		try {
//			// 若轮询间隔(5s)大于内部耽搁时间(2s)，则两轮总共间隔5s,两轮之间间隔3s
//			// 若轮询间隔(2s)小于等于内部耽搁时间(5s)，则两轮总共间隔6s,两轮之间间隔1s
//			Thread.sleep(2*1000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("==3.1=={"+Thread.currentThread().getName()+","+LocalDateTime.now()+","+date+"}");
//		local.set(LocalDateTime.now());
//	}
}
