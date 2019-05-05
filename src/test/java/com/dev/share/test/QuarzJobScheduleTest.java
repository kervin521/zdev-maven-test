package com.dev.share.test;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.google.common.collect.Maps;

/**
 * 项目: SF_Equipment_Diagnosis
 * 描述:
 * 
 * @author ZhangYi
 * @date 2019-04-11 14:25:42
 *       版本: v1.0
 *       JDK: 1.8
 */
@Component
public class QuarzJobScheduleTest {
	@Autowired
	private org.quartz.Scheduler scheduler;

	@Scheduled(cron = "0/2 * * * * ?")
	public void scanQuarzJob() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		String packageName = "com.hollysys.smartfactory.equipmentdiagnosis.system.job";
		String path = ResourceUtils.CLASSPATH_URL_PREFIX + packageName.replaceAll("\\.", "\\/");
		PathMatchingResourcePatternResolver paths = new PathMatchingResourcePatternResolver(getClass().getClassLoader());
		Resource[] resources = paths.getResources(path + "/*.class");
		for (Resource resource : resources) {
			String fileName = resource.getFilename();
			if (fileName != null && fileName.endsWith(".class")) {
				fileName = fileName.substring(0, fileName.lastIndexOf(".class"));
				String jobClassName = packageName + "." + fileName;
				if (!map.containsKey(jobClassName)) {
					Class<Job> clazz = (Class<Job>) Class.forName(jobClassName);
					clazz.newInstance();
					JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(fileName, "quartz").build();
					if (!scheduler.checkExists(jobDetail.getKey())) {
						scheduler.addJob(jobDetail, false, true);
					}
				}
			}
		}
	}
}
