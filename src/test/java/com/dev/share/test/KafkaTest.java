package com.dev.share.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.dev.share.kafka.KafkaConsumerFactory;
import com.dev.share.kafka.KafkaProducerFactory;
import com.dev.share.kafka.KafkaRecordInfo;

public class KafkaTest {
	public static final String kafka_server = "localhost:9092";
	public static final String kafka_zookeeper = "localhost:2181";
	public static Map<String, String> map = new HashMap<>();
	public static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(4);

	public static void producer() {
		KafkaProducerFactory producer = new KafkaProducerFactory();
		producer.init(kafka_server, false, kafka_zookeeper, "all");
		schedule.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				String data = "[" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "]MO/Report消息";
				KafkaRecordInfo obj = new KafkaRecordInfo();
				obj.setContent(data);
				obj.setName("" + new Date().getTime() % 10);
				producer.send(obj);
			}
		}, 1, 1, TimeUnit.MINUTES);
	}

	public static void consumer(String groupId) {
		KafkaConsumerFactory consumer = new KafkaConsumerFactory();
		consumer.init(kafka_server, false, kafka_zookeeper, groupId, "all");
		while (true) {
			List<KafkaRecordInfo> list = consumer.getData();
			for (KafkaRecordInfo value : list) {
				String data = groupId + "--->" + value.getContent();
//				System.out.println(data);
				map.put(data, data);
			}
		}
	}

	public static void main(String[] args) {
		producer();
		for (int i = 0; i < 3; i++) {
			final String groupId = "groupId_" + i;
			schedule.submit(new Runnable() {
				@Override
				public void run() {
					consumer(groupId);
				}
			});
		}
		schedule.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("\n\n=================================================================================");
				String data = StringUtils.join(map.keySet(), "\n");
				if (StringUtils.isNotBlank(data))
					System.out.println(data);
				System.out.println("=================================================================================\\n\\n");
			}
		}, 1, 10, TimeUnit.SECONDS);
	}
}
