package com.dev.share.common;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 项目: SF_Equipment_Diagnosis
 * 描述: 系统同步异步计划配置
 * xmlns:task="http://www.springframework.org/schema/task"
 * <task:executor id="executor" pool-size="5"/>
 * <task:scheduler id="scheduler" pool-size="10"/>
 * <task:annotation-driven executor="executor" scheduler="scheduler"/>
 * 
 * @author ZhangYi
 * @date 2019-04-03 13:48:43
 *       版本: v1.0
 *       JDK: 1.8
 */
@Configuration
public class AsynScheduleConfig extends AsyncConfigurerSupport implements SchedulingConfigurer {
	/**
	 * CPU数量
	 */
	public static final int SYS_CORE = Runtime.getRuntime().availableProcessors();

	@Override
	public Executor getAsyncExecutor() {
		SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
		executor.setConcurrencyLimit(SYS_CORE * 2);
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		SimpleAsyncUncaughtExceptionHandler handler = new SimpleAsyncUncaughtExceptionHandler();
		return handler;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar registrar) {
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(SYS_CORE * 2);
		registrar.setScheduler(schedule);
	}

}
