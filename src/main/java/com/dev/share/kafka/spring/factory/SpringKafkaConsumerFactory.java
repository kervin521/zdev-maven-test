package com.dev.share.kafka.spring.factory;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import com.dev.share.kafka.spring.KafkaConsumerService;
import com.dev.share.kafka.spring.support.CommonKafkaConfig;
/**
 * @decription Kafka服务
 * @author yi.zhang
 * @time 2017年6月8日 下午2:39:42
 * @since 1.0
 * @jdk 1.8
 */
public class SpringKafkaConsumerFactory extends CommonKafkaConfig{
	private static Logger logger = LogManager.getLogger(SpringKafkaConsumerFactory.class);
	private ConcurrentMessageListenerContainer<String, String> listener = null;
	private String groupId;
	public SpringKafkaConsumerFactory(){
		super();
	}
	public SpringKafkaConsumerFactory(String servers,boolean isZookeeper,String zookeeper_servers,String groupId,String acks){
		super(servers, isZookeeper, zookeeper_servers, acks);
		this.groupId = groupId;
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
	private void init() {
		try {
			Map<String, Object> consumer_config = new HashMap<String, Object> ();
			if(isZookeeper&&zookeeper_servers!=null){
				consumer_config.put("zookeeper.connect", zookeeper_servers);
			}
			consumer_config.put("bootstrap.servers", servers);
			// 消费者的组idss
			consumer_config.put("group.id", groupId);
			consumer_config.put("enable.auto.commit", KAFKA_AUTO_COMMIT_ENABLED);
			consumer_config.put("auto.commit.interval.ms", 1*1000);
			// 从poll(拉)的回话处理时长
			consumer_config.put("session.timeout.ms", 30*1000);
//			consumer_config.put("isolation.level", IsolationLevel.READ_COMMITTED.toString().toLowerCase(Locale.ROOT));
			consumer_config.put("key.deserializer", StringDeserializer.class.getName());
			consumer_config.put("value.deserializer", StringDeserializer.class.getName());
			DefaultKafkaConsumerFactory<String,String> factory = new DefaultKafkaConsumerFactory<String,String>(consumer_config);
			ContainerProperties container = new ContainerProperties(KAFKA_TOPIC);
			container.setMessageListener(new KafkaConsumerService());
			ConcurrentMessageListenerContainer<String, String> listener = new ConcurrentMessageListenerContainer<String, String>(factory, container);
			listener.setConcurrency(3);
//			logger.getLogger("org.apache.kafka.clients").setLevel(Level.WARN);
			listener.start();
		} catch (Exception e) {
			logger.error("-----Kafka Config init Error-----", e);
		}
	}
	public void start() {
		init();
		while(true) {
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭服务
	 */
	public void stop(){
		if(listener!=null){
			listener.stop();
		}
	}
}
