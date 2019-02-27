package com.dev.share.test;

import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlet.InstrumentedFilterContextListener;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.dev.share.ThreadPool.AbstractRunnable;
import com.dev.share.metrics.MetricsHandler;
import com.dev.share.util.CipherUtils;
import com.dev.share.util.StringUtils;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.hotspot.DefaultExports;

public class JettyTest {
	public static int core = Runtime.getRuntime().availableProcessors();
	public static ExecutorService shedule = Executors.newScheduledThreadPool(core*2);
	public static void test() {
		Meter meter = MetricsHandler.meter("metric-meter");
		Timer timer = MetricsHandler.timer("metric-timer");
		for(int i=0;i<10;i++) {
			shedule.execute(new AbstractRunnable(i) {
				@Override
				public void run() {
					long time = System.currentTimeMillis();
					final int worker = this.getWorker();
					while(true) {
						long start = System.currentTimeMillis();
						String result = null;
						meter.mark();
						Context ctx = timer.time();
						try{
							Thread.sleep(5*1000l);
							String target = "【春天的风】来自106，请在30秒内按页面提示提交，若非本人操作，请忽略！";
//							String target = "2018.12.29_"+new Date().getTime()+"_"+new Random().nextInt(size);
//							List<String> phones = new ArrayList<>();
//							for(int i=1000000;i<1100000;i++) {
//								String phone = "138"+i+""+new Random().nextInt(10);
//								phones.add(phone);
//							}
//							String target = StringUtils.join(phones, ",");
							result = CipherUtils.hmac(target);
							long delay = System.currentTimeMillis();
							System.out.println("------------------------------------["+worker+"]{use:"+StringUtils.time(start, delay)+",time:"+StringUtils.time(time, delay)+",result:"+result);
//							long diff = Double.valueOf((delay-time)/1000).longValue();
//							if(diff%10==0) {
//								System.out.println("------------------------------------["+worker+"]{time:"+StringUtils.time(time, delay)+",target:"+result+",result:"+result);
//							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							ctx.close();
						}
					}
				}
			});
		}
	}
	public static void main(String[] args) {
		try {
			Server server = new Server(8080);
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setMaxFormContentSize(Integer.MAX_VALUE);
			context.setMaxFormKeys(Integer.MAX_VALUE);
			context.setContextPath("/");
			context.addEventListener(new MetricsServlet.ContextListener() {
				@Override
				protected MetricRegistry getMetricRegistry() {
					return MetricsHandler.registry();
				}
			});
			context.addEventListener(new HealthCheckServlet.ContextListener() {
				@Override
				protected HealthCheckRegistry getHealthCheckRegistry() {
					return MetricsHandler.healthCheck();
				}
			});
//			context.addEventListener(new InstrumentedFilterContextListener() {
//				@Override
//				protected MetricRegistry getMetricRegistry() {
//					return MetricsHandler.registry();
//				}
//			});
//			context.addFilter(new FilterHolder(InstrumentedFilter.class), "/enums/*", EnumSet.allOf(DispatcherType.class));
			server.setHandler(context);
			context.addServlet(new ServletHolder(AdminServlet.class), "/enums");
			context.addServlet(new ServletHolder(MetricsServlet.class), "/enums/metrics");
//			context.addServlet(new ServletHolder(HealthCheckServlet.class), "/enums/healthcheck");
//			context.addServlet(new ServletHolder(PingServlet.class), "/enums/ping");
//			context.addServlet(new ServletHolder(ThreadDumpServlet.class), "/enums/threads");
//			context.addServlet(new ServletHolder(CpuProfileServlet.class), "/enums/pprof");
			context.addServlet(new ServletHolder(io.prometheus.client.exporter.MetricsServlet.class), "/enums/prometheus");
//			DefaultExports.initialize();
			DropwizardExports prometheus = new DropwizardExports(MetricsHandler.registry());
	        prometheus.register();
			System.setProperty("metric.enabled","true");
			System.setProperty("metric.mode","console");
			System.setProperty("metric.jvm.enabled","false");
			System.setProperty("metric.logger.level","info");
			server.start();
			test();
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();// 异常信息
		} catch (Exception e) {
			e.printStackTrace();// 异常信息
		}
	}
}
