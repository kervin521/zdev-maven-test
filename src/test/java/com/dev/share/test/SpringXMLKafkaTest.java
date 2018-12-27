package com.dev.share.test;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dev.share.kafka.KafkaRecordInfo;
import com.dev.share.kafka.spring.KafkaProducerService;


public class SpringXMLKafkaTest {
//	public static ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-kafka.xml");
	public static FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:spring-kafka.xml");
	public static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(4);
	public static void producer() {
		ctx.registerShutdownHook();
		ctx.start();
		final KafkaProducerService kafkaProducerService = ctx.getBean(KafkaProducerService.class);
		schedule.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				String data = "["+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"]MO/Report消息";
				KafkaRecordInfo obj = new KafkaRecordInfo();
				obj.setContent(data);
				obj.setName(""+new Date().getTime()%10);
				kafkaProducerService.sendMsg(obj);
				System.out.println("==========================================Send Success=======================================");
			}
		}, 1, 10, TimeUnit.SECONDS);
	}
	
	public static void main(String[] args) {
		producer();
	}
}
