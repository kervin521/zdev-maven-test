package com.dev.share.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @decription Kafka服务
 * @author yi.zhang
 * @time 2017年6月8日 下午2:39:42
 * @since 1.0
 * @jdk 1.8
 */
public class KafkaConsumerFactory {
	private static Logger logger = LogManager.getLogger(KafkaConsumerFactory.class);
	private static String KAFKA_TOPIC = "KAFKA_TOPIC";
	public static int KAFKA_CONSUMER_BATCCH_SIZE = 100;
	private KafkaConsumer<String, KafkaRecordInfo> consumer = null;

	private String servers;
	private boolean isZookeeper;
	private String zookeeper_servers;
	private String acks;
	private String groupId;

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public boolean isZookeeper() {
		return isZookeeper;
	}

	public void setZookeeper(boolean isZookeeper) {
		this.isZookeeper = isZookeeper;
	}

	public String getZookeeper_servers() {
		return zookeeper_servers;
	}

	public void setZookeeper_servers(String zookeeper_servers) {
		this.zookeeper_servers = zookeeper_servers;
	}

	public String getAcks() {
		return acks;
	}

	public void setAcks(String acks) {
		this.acks = acks;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @decription 初始化配置
	 * @author yi.zhang
	 * @time 2017年6月2日 下午2:15:57
	 */
	public void init(String servers, boolean isZookeeper, String zookeeper_servers, String groupId, String acks) {
		try {
			Properties consumer_config = new Properties();
			if (isZookeeper && zookeeper_servers != null) {
				consumer_config.put("zookeeper.connect", zookeeper_servers);
			}
			consumer_config.put("bootstrap.servers", servers);
			// 消费者的组id
			consumer_config.put("group.id", groupId);
			consumer_config.put("enable.auto.commit", true);
			consumer_config.put("auto.commit.interval.ms", 10 * 1000);
			// 从poll(拉)的回话处理时长
			consumer_config.put("session.timeout.ms", 30 * 1000);
			consumer_config.put("key.deserializer", StringDeserializer.class.getName());
			consumer_config.put("value.deserializer", StringDeserializer.class.getName());
			consumer = new KafkaConsumer<String, KafkaRecordInfo>(consumer_config);
			// 订阅主题列表topic
			consumer.subscribe(Arrays.asList(KAFKA_TOPIC));
		} catch (Exception e) {
			logger.error("-----Kafka Config init Error-----", e);
		}
	}

	/**
	 * 关闭服务
	 */
	public void close() {
		if (consumer != null) {
			consumer.close();
		}
	}

	/**
	 * @decription Kafka消费者
	 * @author yi.zhang
	 * @time 2017年6月8日 下午2:44:32
	 * @return
	 */
	public KafkaConsumer<String, KafkaRecordInfo> getConsumer() {
		if (consumer == null) {
			init(servers, isZookeeper, zookeeper_servers, groupId, acks);
		}
		return consumer;
	}

	public List<KafkaRecordInfo> getData() {
		List<KafkaRecordInfo> data = new ArrayList<KafkaRecordInfo>();
		ConsumerRecords<String, KafkaRecordInfo> records = consumer.poll(1000);
		for (ConsumerRecord<String, KafkaRecordInfo> record : records) {
			KafkaRecordInfo value = record.value();
			data.add(value);
		}
		return data;
	}

}
