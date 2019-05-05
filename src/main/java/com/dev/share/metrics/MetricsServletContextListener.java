package com.dev.share.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlet.InstrumentedFilterContextListener;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;

/**
 * <listener>
 * <listener-class>com.dev.share.metrics.MetricsServletContextListener</listener-class>
 * </listener>
 * <listener>
 * <listener-class>com.dev.share.metrics.MetricsHealthCheckServletContextListener</listener-class>
 * </listener>
 * <filter>
 * <filter-name>instrumentedFilter</filter-name>
 * <filter-class>com.codahale.metrics.servlet.InstrumentedFilter</filter-class>
 * </filter>
 * <filter-mapping>
 * <filter-name>instrumentedFilter</filter-name>
 * <url-pattern>/*</url-pattern>
 * </filter-mapping>
 * <servlet>
 * <servlet-name>metrics</servlet-name>
 * <servlet-class>com.codahale.metrics.servlets.AdminServlet</servlet-class>
 * </servlet>
 * <servlet-mapping>
 * <servlet-name>metrics</servlet-name>
 * <url-pattern>/metrics/*</url-pattern>
 * </servlet-mapping>
 * 作者: ZhangYi
 * 时间: 2019年1月24日 下午3:57:43
 * 版本: v1.0
 * JDK: 1.8.192
 * 
 * @company 空间畅想
 */
public class MetricsServletContextListener extends MetricsServlet.ContextListener {
	@Override
	protected MetricRegistry getMetricRegistry() {
		return MetricsHandler.registry();
	}

	public class MetricsHealthCheckServletContextListener extends HealthCheckServlet.ContextListener {
		private final HealthCheckRegistry registry = new HealthCheckRegistry();

		@Override
		protected HealthCheckRegistry getHealthCheckRegistry() {
			return registry;
		}
	}

	public class MetricsInstrumentedFilterContextListener extends InstrumentedFilterContextListener {
		@Override
		protected MetricRegistry getMetricRegistry() {
			return MetricsHandler.registry();
		}
	}
}
