package com.dev.share.kafka.spring;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

import com.dev.share.kafka.KafkaRecordInfo;
import com.dev.share.kafka.spring.support.AbstractKafkaProducerService;

public class KafkaProducerService extends AbstractKafkaProducerService<KafkaRecordInfo> {
	private static Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

	public ProducerListener<String, String> callback() {
		ProducerListener<String, String> callback = new ProducerListener<String, String>() {
			@Override
			public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
				logger.info("--[Kafka producer(" + topic + ")] send success,data:" + value + "!");
				System.out.println("\n\n\n\n----------kafka producer success!topic:" + topic + ",data:" + value + "\n\n\n\n");
			}

			@Override
			public void onError(String topic, Integer partition, String key, String value, Exception e) {
				logger.error("--[Kafka producer(" + topic + ")] send error,data:" + value + "!", e);
				System.out.println("\n\n\n\n----------kafka producer Error!" + e.getMessage() + "\n\n\n\n");
			}

			@Override
			public boolean isInterestedInSuccess() {
				return true;
			}
		};
		return callback;
	}

	/**
	 * (生产者推送数据)
	 * 数据发布到MQ上类似广播其他网关平台
	 * 
	 * @param bean
	 */
	public void sendMsg(KafkaRecordInfo bean) {
		String topic = KAFKA_TOPIC;
		try {
			boolean flag = send(topic, bean);
			if (!flag) {
				// 此主要是json转化异常情况
				System.out.println("---------------------send success!");
			}
		} catch (Exception e) {
			logger.error("---topic[" + topic + "] publish error !", e);
		}
	}
}
