package com.dev.share.test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.codahale.metrics.Meter;
import com.codahale.metrics.Slf4jReporter;
import com.dev.share.kafka.KafkaRecordInfo;
import com.dev.share.kafka.spring.KafkaProducerService;
import com.dev.share.metrics.MetricsHandler;

import ch.qos.logback.classic.Level;

public class SpringXMLKafkaTest {
	private static Logger logger = LoggerFactory.getLogger(SpringXMLKafkaTest.class);
	public static int core = Runtime.getRuntime().availableProcessors();
	public static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(core * 2);
	public static Meter meter = MetricsHandler.meter("Kafka-Producer");
	public static FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:spring-context.xml");

	public static void producer() throws IOException, InterruptedException, ConfigurationException {
		ctx.registerShutdownHook();
		ctx.start();
		Slf4jReporter slf4j = MetricsHandler.slf4j();
		slf4j.start(1, TimeUnit.SECONDS);
		final KafkaProducerService kafkaProducerService = ctx.getBean(KafkaProducerService.class);
		schedule.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				String data = "[" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "]MO/Report消息";
				KafkaRecordInfo obj = new KafkaRecordInfo();
				obj.setContent(data);
				obj.setName("" + new Date().getTime() % 10);
				kafkaProducerService.sendMsg(obj);
				meter.mark();
				logger.info("==========================================Send Success=======================================");
			}
		}, 1, 10, TimeUnit.SECONDS);
	}

	public static void main(String[] args) throws Exception {
		producer();
	}
}
