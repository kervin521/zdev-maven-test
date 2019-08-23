package com.dev.share.metrics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Slf4jReporter.LoggingLevel;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.CachedThreadStatesGaugeSet;
import com.codahale.metrics.jvm.ClassLoadingGaugeSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.dev.share.ThreadPool.ShutdownHook;
/**
 * @description 系统性能监控
 * @author ZhangYi
 * @date 2019/07/31 16:48:35
 * @version 1.0.0 
 * @Jdk 1.8
 */
public final class MetricsHandler {
    private static final MetricRegistry REGISTRY = new MetricRegistry();
    private static final HealthCheckRegistry HEALTHCHECK = new HealthCheckRegistry();
    private static final Map<String, Meter> METERS = new ConcurrentHashMap<>();
    private static final Map<String, Counter> COUNTERS = new ConcurrentHashMap<>();
    private static final Map<String, Histogram> HISTOGRAMS = new ConcurrentHashMap<>();
    private static final Map<String, Timer> TIMERS = new ConcurrentHashMap<>();
    static {
        init();
    }
    private MetricsHandler() {}
    /**
     * @description 自动注册Metric监控
     * @author ZhangYi
     * @date 2019/07/31 16:37:51
     */
    public static void init() {
        boolean enabled = Boolean.parseBoolean(System.getProperty("metric.enabled", "true"));
        if (enabled) {
            long period = 5000;
            String time = System.getProperty("metric.period", period+"");// 默认5秒
            if (StringUtils.isNotBlank(time)) {
                if (NumberUtils.isNumber(time)) {
                    period = NumberUtils.toLong(time);
                } else {
                    if (time.contains("\\*")) {
                        for (String num : time.split("\\*")) {
                            period *= NumberUtils.toLong(num);
                        }
                    }
                }
            }
            ScheduledReporter monitor = null;// 自动加入Metrics性能监控
            String mode = System.getProperty("metric.mode", "console");// console/slf4j
            if (mode.equalsIgnoreCase("console")) {
                monitor = console();
            } else {
                monitor = MetricsHandler.slf4j();
            }
            monitor.start(period, TimeUnit.MILLISECONDS);
            ShutdownHook.add(monitor);
        }
    }

    private static void jvm() {
        boolean enabled = Boolean.parseBoolean(System.getProperty("metric.jvm.enabled"));
        if (enabled) {
            REGISTRY.register("jvm.mem", new MemoryUsageGaugeSet());
            REGISTRY.register("jvm.gc", new GarbageCollectorMetricSet());
            REGISTRY.register("jvm.thread", new ThreadStatesGaugeSet());
            REGISTRY.register("jvm.class.load", new ClassLoadingGaugeSet());
            REGISTRY.register("jvm.cache", new CachedThreadStatesGaugeSet(1, TimeUnit.SECONDS));
        }
    }

    public static ConsoleReporter console() {
        ConsoleReporter console = ConsoleReporter.forRegistry(REGISTRY).build();
        jvm();
        return console;
    }

    public static Slf4jReporter slf4j() {
        String level = System.getProperty("metric.logger.level", "debug");
        LoggingLevel mlevel = LoggingLevel.valueOf(level.toUpperCase()) == null ? LoggingLevel.DEBUG : LoggingLevel.valueOf(level.toUpperCase());
        Slf4jReporter slf4j = Slf4jReporter.forRegistry(REGISTRY).withLoggingLevel(mlevel).build();
        jvm();
        return slf4j;
    }

    public static MetricRegistry registry() {
        return REGISTRY;
    }

    public static HealthCheckRegistry healthCheck() {
        return HEALTHCHECK;
    }

    public static Meter meter(String name) {
        Meter metric = REGISTRY.meter(name);
        METERS.put(name, metric);
        return metric;
    }

    public static Counter counter(String name) {
        Counter metric = REGISTRY.counter(name);
        COUNTERS.put(name, metric);
        return metric;
    }

    public static Histogram histogram(String name) {
        Histogram metric = REGISTRY.histogram(name);
        HISTOGRAMS.put(name, metric);
        return metric;
    }

    public static Timer timer(String name) {
        Timer metric = REGISTRY.timer(name);
        TIMERS.put(name, metric);
        return metric;
    }

    public static String printlnMeters() {
        StringBuilder print = new StringBuilder("\n\n");
        if (!METERS.isEmpty()) {
            for (Map.Entry<String,Meter> entry : METERS.entrySet()) {
                Meter obj = entry.getValue();
                String name = entry.getKey();
                long count = obj.getCount();
                double rate1m = obj.getOneMinuteRate();
                double rate5m = obj.getFiveMinuteRate();
                double rate15m = obj.getFifteenMinuteRate();
                double rateavg = obj.getMeanRate();
                print.append("-- Meter[" + name + "] ----------------------------------------------------------------------\n");
                print.append("         count = " + count + "\n");
                print.append("     mean rate = " + String.format("%.2f", rateavg) + " calls/second\n");
                print.append(" 1-minute rate = " + String.format("%.2f", rate1m) + " calls/second\n");
                print.append(" 5-minute rate = " + String.format("%.2f", rate5m) + " calls/second\n");
                print.append("15-minute rate = " + String.format("%.2f", rate15m) + " calls/second\n");
            }
        }
        print.append("\n\n");
        return print.toString();
    }

    public static String printlnCounters() {
        StringBuilder print = new StringBuilder("\n\n");
        if (!COUNTERS.isEmpty()) {
            for (Map.Entry<String,Counter> entry : COUNTERS.entrySet()) {
                Counter obj = entry.getValue();
                String name = entry.getKey();
                long count = obj.getCount();
                print.append("-- Counter[" + name + "] ----------------------------------------------------------------------\n");
                print.append("         count = " + count + "\n");
            }
        }
        print.append("\n\n");
        return print.toString();
    }

    public static String printlnHistograms() {
        StringBuilder print = new StringBuilder("\n\n");
        if (!HISTOGRAMS.isEmpty()) {
            for (Map.Entry<String,Histogram> entry : HISTOGRAMS.entrySet()) {
                Histogram obj = entry.getValue();
                String name = entry.getKey();
                long count = obj.getCount();
                Snapshot snapshot = obj.getSnapshot();
                double pct75th = snapshot.get75thPercentile();
                double pct95th = snapshot.get95thPercentile();
                double pct98th = snapshot.get98thPercentile();
                double pct99th = snapshot.get99thPercentile();
                double pct999th = snapshot.get999thPercentile();
                double max = snapshot.getMax();
                double min = snapshot.getMin();
                double avg = snapshot.getMean();
                double median = snapshot.getMedian();
                double stddev = snapshot.getStdDev();
                print.append("-- Histogram[" + name + "] ----------------------------------------------------------------------\n");
                print.append("         count = " + count + "\n");
                print.append("           min = " + String.format("%.2f", min) + " milliseconds\n");
                print.append("           max = " + String.format("%.2f", max) + " milliseconds\n");
                print.append("          mean = " + String.format("%.2f", avg) + " milliseconds\n");
                print.append("        stddev = " + String.format("%.2f", stddev) + " milliseconds\n");
                print.append("        median = " + String.format("%.2f", median) + " milliseconds\n");
                print.append("          75% <= " + String.format("%.2f", pct75th) + " milliseconds\n");
                print.append("          95% <= " + String.format("%.2f", pct95th) + " milliseconds\n");
                print.append("          98% <= " + String.format("%.2f", pct98th) + " milliseconds\n");
                print.append("          99% <= " + String.format("%.2f", pct99th) + " milliseconds\n");
                print.append("        99.9% <= " + String.format("%.2f", pct999th) + " milliseconds\n");
            }
        }
        print.append("\n\n");
        return print.toString();
    }

    public static String printlnTimers() {
        StringBuilder print = new StringBuilder("\n\n");
        if (!TIMERS.isEmpty()) {
            for (Map.Entry<String,Timer> entry : TIMERS.entrySet()) {
                Timer obj = entry.getValue();
                String name = entry.getKey();
                long count = obj.getCount();
                double rate1m = obj.getOneMinuteRate();
                double rate5m = obj.getFiveMinuteRate();
                double rate15m = obj.getFifteenMinuteRate();
                double rateavg = obj.getMeanRate();
                Snapshot snapshot = obj.getSnapshot();
                double pct75th = snapshot.get75thPercentile();
                double pct95th = snapshot.get95thPercentile();
                double pct98th = snapshot.get98thPercentile();
                double pct99th = snapshot.get99thPercentile();
                double pct999th = snapshot.get999thPercentile();
                double max = snapshot.getMax();
                double min = snapshot.getMin();
                double avg = snapshot.getMean();
                double median = snapshot.getMedian();
                double stddev = snapshot.getStdDev();
                print.append("-- Timer[" + name + "] ----------------------------------------------------------------------\n");
                print.append("         count = " + count + "\n");
                print.append("     mean rate = " + String.format("%.2f", rateavg) + " calls/second\n");
                print.append(" 1-minute rate = " + String.format("%.2f", rate1m) + " calls/second\n");
                print.append(" 5-minute rate = " + String.format("%.2f", rate5m) + " calls/second\n");
                print.append("15-minute rate = " + String.format("%.2f", rate15m) + " calls/second\n");
                print.append("           min = " + String.format("%.2f", min) + " milliseconds\n");
                print.append("           max = " + String.format("%.2f", max) + " milliseconds\n");
                print.append("          mean = " + String.format("%.2f", avg) + " milliseconds\n");
                print.append("        stddev = " + String.format("%.2f", stddev) + " milliseconds\n");
                print.append("        median = " + String.format("%.2f", median) + " milliseconds\n");
                print.append("          75% <= " + String.format("%.2f", pct75th) + " milliseconds\n");
                print.append("          95% <= " + String.format("%.2f", pct95th) + " milliseconds\n");
                print.append("          98% <= " + String.format("%.2f", pct98th) + " milliseconds\n");
                print.append("          99% <= " + String.format("%.2f", pct99th) + " milliseconds\n");
                print.append("        99.9% <= " + String.format("%.2f", pct999th) + " milliseconds\n");
            }
        }
        print.append("\n\n");
        return print.toString();
    }
}
