package com.dev.share.kafka.spring.support;

import java.util.Random;

public class CommonKafkaConfig {
	public static String KAFKA_TOPIC = "spring_idc_topic2";
	public static int KAFKA_CONSUMER_BATCCH_SIZE = 100;
	public static boolean KAFKA_AUTO_COMMIT_ENABLED = false;
	
	protected String servers;
	protected boolean isZookeeper;
	protected String zookeeper_servers;
	protected String acks;
	
	public CommonKafkaConfig(){
	}
	public CommonKafkaConfig(String servers, boolean isZookeeper, String zookeeper_servers, String acks){
		this.servers = servers;
		this.isZookeeper = isZookeeper;
		this.zookeeper_servers = zookeeper_servers;
		this.acks = acks;
	}
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
	public void setTopic(String topic) {
		KAFKA_TOPIC = topic;
	}
	public String getTopic() {
		return KAFKA_TOPIC;
	}
	public void setAutoCommit(Boolean enabled) {
		KAFKA_AUTO_COMMIT_ENABLED = enabled;
	}
	public String getRandomId() {
		String randomId = "group_"+new Random().nextInt(10000);
		return randomId;
	}
}
