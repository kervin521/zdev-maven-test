package com.dev.share.kafka.spring.support;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import com.alibaba.fastjson.JSON;


public abstract class AbstractKafkaConsumerService<T> extends CommonKafkaConfig implements AcknowledgingMessageListener<String, String> {
	private static Logger logger = LoggerFactory.getLogger(AbstractKafkaConsumerService.class);
	public abstract boolean handle(T obj);
	
	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(ConsumerRecord<String, String> obj, Acknowledgment ack) {
		String topic = obj.topic();
		String value = obj.value();
		String key = obj.key();
		try {
			T bean = (T) value;
			if(!StringUtils.isEmpty(key)) {
				Class<T> clazz = (Class<T>) Class.forName(obj.key());
				bean = JSON.parseObject(value, clazz);
			}
			boolean flag = handle(bean);
			if(flag&&!KAFKA_AUTO_COMMIT_ENABLED&&ack!=null) {
				ack.acknowledge();
			}
		} catch (Exception e) {
			logger.error("--[Kafka-consumer("+topic+")]Data io error ! ",e);
		}
	}
}
