package com.dev.share.kafka.spring.support;

import java.io.Serializable;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;

import com.alibaba.fastjson.JSON;

public abstract class AbstractKafkaProducerService<T extends Serializable> extends CommonKafkaConfig {
	private static Logger logger = LoggerFactory.getLogger(AbstractKafkaProducerService.class);
	private KafkaTemplate<String, String> template;

//	public KafkaTemplate<String, String> getTemplate() {
//		return template;
//	}

	public void setTemplate(KafkaTemplate<String, String> template) {
		this.template = template;
	}

	public abstract ProducerListener<String, String> callback();

	public boolean send(String topic, T bean) {
		boolean flag = true;
		try {
			String key = null;
//			key = bean.getClass().getName();
			String data = JSON.toJSONString(bean);
			template.send(new ProducerRecord<String, String>(topic, key, data));
		} catch (Exception e) {
			logger.error("---kafka[" + topic + "] send error !", e);
			flag = false;
		}
		return flag;
	}
}
