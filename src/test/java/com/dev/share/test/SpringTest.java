package com.dev.share.test;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.codahale.metrics.Meter;
import com.codahale.metrics.Slf4jReporter;
import com.dev.share.cache4j.SynCache;
import com.dev.share.metrics.MetricsHandler;

import ch.qos.logback.classic.Level;

public class SpringTest {
	private static Logger logger = LoggerFactory.getLogger(SpringXMLKafkaTest.class);
	public static int core = Runtime.getRuntime().availableProcessors();
	public static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(core * 2);
	public static Meter meter = MetricsHandler.meter("Spring-Meter");
	public static AtomicInteger atomic = new AtomicInteger();
	public static FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:spring-context.xml");

	public static void init() {
		ctx.registerShutdownHook();
		ctx.start();
		Slf4jReporter slf4j = MetricsHandler.slf4j();
		slf4j.start(5, TimeUnit.SECONDS);
		int size = 10;
		for (int i = 0; i < size; i++) {
			schedule.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						String threadName = Thread.currentThread().getName();
						String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
						String random = "" + new Date().getTime() % 2;
						long type = System.currentTimeMillis() % 3;
						String name = "apiUniqueProcessInnerCache";
						String sign = "[sign]";
						String content = sign + "123456_" + random;
						SynCache cache = ctx.getBean("apiUniqueProcessInnerCache", SynCache.class);
						if (type != 0) {
							if (type == 1) {
								cache = ctx.getBean("mtRouteResultProcessInnerCache", SynCache.class);
								name = "mtRouteResultProcessInnerCache";
							} else {
								cache = ctx.getBean("appProcessInnerCache", SynCache.class);
								name = "appProcessInnerCache";
							}
						}
						String cacheKey = "cache_key";
						boolean flag = false;
						@SuppressWarnings("unchecked")
						Map<String, String> simiMap = (Map<String, String>) cache.get(cacheKey);
						if (simiMap != null && simiMap.size() > 0) {
							simiMap = new ConcurrentHashMap<>(simiMap);
							// 先判断相同
							for (Entry<String, String> entry : simiMap.entrySet()) {
								if (content.startsWith(entry.getKey())) {
									flag = true;
								}
							}
						} else {
							simiMap = new ConcurrentHashMap<String, String>();
						}
						if (!flag) {
							simiMap.put(sign, "uniqueID_" + atomic.incrementAndGet());
							cache.put(cacheKey, simiMap);
						}
						meter.mark();
						System.out.println("======>[" + date + "]|[" + threadName + "]|[" + name + "," + flag + "] Success=======================================");
//  						logger.info("==========================================>["+name+","+flag+"] Success=======================================");
						try {
							Thread.sleep(20 * 1000l);
						} catch (InterruptedException e) {
							e.printStackTrace();// 异常信息
						}
					}
				}
			});
		}
	}

	public static void main(String[] args) {
		init();
	}
}
