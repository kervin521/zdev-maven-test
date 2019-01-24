package com.dev.share.kafka.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.codahale.metrics.Meter;
import com.dev.share.kafka.spring.support.AbstractKafkaConsumerService;
import com.dev.share.metrics.MetricsHandler;

public class KafkaConsumerService extends AbstractKafkaConsumerService<Object> {
	private static Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
	public static Meter meter = MetricsHandler.meter("Kafka-Consumer");
	@Override
	public boolean handle(Object obj) {
		System.out.println("\n\n=================================================================================");
		boolean flag = true;
		try {
			String value = null;
			if(obj instanceof String) {
				value = (String) obj;
			}else {
				value = JSON.toJSONString(obj);
			}
			meter.mark();
			System.out.println("[Kafka-consumer]"+value);
		}catch (Exception e) {
			logger.error("--[Kafka-consumer]Data io error ! ",e);
			flag = false;
		}
		System.out.println("=================================================================================\\n\\n");
		return flag;
	}
}
