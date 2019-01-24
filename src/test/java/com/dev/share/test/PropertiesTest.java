package com.dev.share.test;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class PropertiesTest {
	public static void main(String[] args) throws ConfigurationException {
		String path = "E:\\Workspace\\KJCX\\zdev-maven-test\\target\\classes\\";
		System.setProperty("channelId", "96");
		System.setProperty("kafka", "kafka");
		String location =path+ "kafka.properties";
		PropertiesConfiguration config  = new PropertiesConfiguration(location);
		config.setReloadingStrategy(new FileChangedReloadingStrategy());
        config.setAutoSave(true);
        config.setProperty("kafka.enabled", false);
	}
}
