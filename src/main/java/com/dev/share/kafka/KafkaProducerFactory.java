package com.dev.share.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**
 * @decription Kafka服务
 * @author yi.zhang
 * @time 2017年6月8日 下午2:39:42
 * @since 1.0
 * @jdk 1.8
 */
public class KafkaProducerFactory {
	private static Logger logger = LogManager.getLogger(KafkaProducerFactory.class);
	private static String KAFKA_TOPIC = "KAFKA_TOPIC";
	public static int KAFKA_CONSUMER_BATCCH_SIZE = 100;
	private KafkaProducer<String, KafkaRecordInfo> producer = null;
	private String servers;
	private boolean isZookeeper;
	private String zookeeper_servers;
	private String acks;
	
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
	/**
	 * @decription 初始化配置
	 * @author yi.zhang
	 * @time 2017年6月2日 下午2:15:57
	 */
	public void init(String servers,boolean isZookeeper,String zookeeper_servers,String acks) {
		try {
			Properties productor_config = new Properties();
			if(isZookeeper&&zookeeper_servers!=null){
				productor_config.put("zk.connect", zookeeper_servers);
			}
			productor_config.put("bootstrap.servers", servers);
			// “所有”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。(The "all" setting we have specified will result in blocking on the full commit of the record, the slowest but most durable setting.)
			productor_config.put("acks", acks);
			// 如果请求失败，生产者也会自动重试，即使设置成０ the producer can automatically retry.
			productor_config.put("retries", 0);
			// The producer maintains buffers of unsent records for each partition.
			productor_config.put("batch.size", 16384);
			// 默认立即发送，这里这是延时毫秒数
			productor_config.put("linger.ms", 1);
			// 生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
			productor_config.put("buffer.memory", 33554432);
			// The key.serializer and value.serializer instruct how to turn the key and value objects the user provides with their ProducerRecord into bytes.
			productor_config.put("key.serializer", StringSerializer.class.getName());
			productor_config.put("value.serializer", StringSerializer.class.getName());
			// 创建kafka的生产者类
			producer = new KafkaProducer<String, KafkaRecordInfo>(productor_config);
		} catch (Exception e) {
			logger.error("-----Kafka Config init Error-----", e);
		}
	}
	/**
	 * 关闭服务
	 */
	public void close(){
		if(producer!=null){
			producer.close();
		}
	}
	/**
	 * @decription 生产者推送数据
	 * @author yi.zhang
	 * @time 2017年6月8日 下午2:24:05
	 * @param data
	 */
	public void send(KafkaRecordInfo data){
		if(producer==null){
			init(servers, isZookeeper, zookeeper_servers, acks);
		}
		producer.send(new ProducerRecord<String,KafkaRecordInfo>(KAFKA_TOPIC,data));
		producer.flush();
	}
	/**
	 * @decription Kafka生产者
	 * @author yi.zhang
	 * @time 2017年6月8日 下午2:44:01
	 * @return
	 */
	public KafkaProducer<String, KafkaRecordInfo> getProducer() {
		if(producer==null){
			init(servers, isZookeeper, zookeeper_servers, acks);
		}
		return producer;
	}

}
