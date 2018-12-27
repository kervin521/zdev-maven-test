package com.dev.share.test;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateFormatUtils;

import com.dev.share.kafka.KafkaRecordInfo;
import com.dev.share.kafka.spring.factory.SpringKafkaConsumerFactory;
import com.dev.share.kafka.spring.factory.SpringKafkaProducerFactory;


public class SpringKafkaTest {
	public static final String kafka_server = "localhost:9092";
	public static final String kafka_zookeeper = "localhost:2181";
	public static Map<String,String> map = new ConcurrentHashMap<>();
	public static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(4);
	public static void producer() {
		SpringKafkaProducerFactory producer = new SpringKafkaProducerFactory(kafka_server, false, kafka_zookeeper, "all");
		producer.start();
		schedule.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				String data = "["+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"]MO/Report消息";
				KafkaRecordInfo obj = new KafkaRecordInfo();
				obj.setContent(data);
				obj.setName(""+new Date().getTime()%10);
				producer.send(obj);
				System.out.println("==========================================Send Success=======================================");
			}
		}, 1, 10, TimeUnit.SECONDS);
	}
	public static void consumer(String groupId) {
		SpringKafkaConsumerFactory consumer = new SpringKafkaConsumerFactory(kafka_server, false, kafka_zookeeper, groupId, "all");
		consumer.start();
	}
	public static void main(String[] args) {
		producer();
		for(int i=0;i<3;i++) {
			final String groupId = "groupId_"+i;
			schedule.submit(new Runnable() {
				@Override
				public void run() {
					consumer(groupId);
				}
			});
		}
	}
}
