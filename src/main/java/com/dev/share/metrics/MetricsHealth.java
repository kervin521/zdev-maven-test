package com.dev.share.metrics;

import java.util.Map;
import java.util.Map.Entry;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

public class MetricsHealth {
	private final static HealthCheckRegistry healthChecks = new HealthCheckRegistry();
	
	public static void register(String name) {
		healthChecks.register(name, new MetricsHealthCheck(true));
	}
	public static void printHealth() {
		final Map<String, HealthCheck.Result> results = healthChecks.runHealthChecks();
		for (Entry<String, HealthCheck.Result> entry : results.entrySet()) {
		    if (entry.getValue().isHealthy()) {
		        System.out.println(entry.getKey() + " is healthy");
		    } else {
		        System.err.println(entry.getKey() + " is UNHEALTHY: " + entry.getValue().getMessage());
		        final Throwable e = entry.getValue().getError();
		        if (e != null) {
		            e.printStackTrace();
		        }
		    }
		}
	}
	public static class MetricsHealthCheck extends HealthCheck {
	    private boolean health;

	    public MetricsHealthCheck(boolean health) {
	        this.health = health;
	    }

	    @Override
	    public HealthCheck.Result check() throws Exception {
	        if (health) {
	            return HealthCheck.Result.healthy();
	        } else {
	            return HealthCheck.Result.unhealthy(" bad health " + health);
	        }
	    }
	}
}
