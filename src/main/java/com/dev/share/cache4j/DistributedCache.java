package com.dev.share.cache4j;

import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 基于redisson造的分布式缓存cache,只附加失效时间
 * 
 * <pre>
 * Created with IntelliJ IDEA.
 * User: lwb
 * Date: 2018/5/16
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 * </pre>
 *
 * @author liwen
 */
public class DistributedCache implements InitializingBean, DisposableBean, Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(DistributedCache.class);

	private transient Map<String, RCacheConfig> cacheConfigs;

	public void destroy() throws Exception {
	}

	public void afterPropertiesSet() throws Exception {
	}

	public RCacheConfig getConfig(String cacheName) {
		return cacheConfigs.get(cacheName);
	}

	public void setCacheConfigs(Map<String, RCacheConfig> cacheConfigs) {
		this.cacheConfigs = cacheConfigs;
	}

}