package com.dev.share.cache4j;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dev.share.spring.SpringContextHolder;
import com.dev.share.util.StringUtils;

import net.sf.cache4j.Cache;
import net.sf.cache4j.CacheConfig;
import net.sf.cache4j.CacheException;
import net.sf.cache4j.impl.CacheConfigImpl;

/***
 * 项目:emgw-core
 * @description 二级缓存
 * 缓存配置参数：
 * cacheId 缓存ID
 * cacheDesc 描述
 * ttl 对象在缓存中的最大生存期，0 - 没有限制
 * idleTime 对象的非活动状态中的高速缓存（空闲时间）的最大时间。0 - 没有限制
 * maxMemorySize 最大内存大小
 * maxSize 在高速缓存的对象的最大数量。
 * type 缓存类型。可能的值有：blocking（闭塞） synchronized（同步） nocache（非缓存）
 * algorithm 该算法是从缓存中移除对象。可能的值有：LRU（最近最少使用）LFU（最常用）FIFO（先入先出）
 * reference 包含在高速缓存对象的引用的类型。可能的值有：strong soft
 * 作者:ZhangYi
 * 时间:2019年1月3日 下午2:52:08
 * 版本:v1.0
 * JDK:1.8.192
 * 
 * @company 空间畅想
 */
public abstract class AbstractCache implements Serializable {
	private static final long serialVersionUID = -1942967698533266300L;
	private static final Logger logger = LoggerFactory.getLogger(AbstractCache.class);
	// 缓存的名称
	private String name = "cacheId";
	// 最大生存期，0 - 没有限制
	// 最大数量
	private int maxSize = 10000;
	// 最大内存
	private long maxMemorySize = 1024 * 1024 * 1024;
	// 移除对象的算法。可能的值有：LRU（最近最少使用）LFU（最常用）FIFO（先入先出）
	private String algorithm = "LRU";
	private long ttl = 0;
	private long idleTime = 0;
	private String reference = "strong";
	// ------------------------------------------
	// cache4j
	private transient Cache cache;
	private DistributedCache distributedCache;

	public void init_dependence() {
		try {
			if (distributedCache == null) {
				distributedCache = SpringContextHolder.getBean(DistributedCache.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (distributedCache == null) {
			throw new IllegalArgumentException("distributedCache constructor error~");
		}
		RCacheConfig config = distributedCache.getConfig(name);
		if (cache == null) {
			try {
				cache = cache(config);
			} catch (CacheException e) {
				logger.error("--初始化JVM二级缓存失败!", e);
			}
		}
		if (cache == null) {
			throw new IllegalArgumentException("cacheImpl constructor error~");
		}
	}

	/**
	 * 缓存配置参数
	 * cacheId 缓存ID
	 * cacheDesc 描述
	 * ttl 对象在缓存中的最大生存期，0 - 没有限制
	 * idleTime 对象的非活动状态中的高速缓存（空闲时间）的最大时间。0 - 没有限制
	 * maxMemorySize 最大内存大小
	 * maxSize 在高速缓存的对象的最大数量。
	 * type 缓存类型。可能的值有：blocking（闭塞） synchronized（同步） nocache（非缓存）
	 * algorithm 该算法是从缓存中移除对象。可能的值有：LRU（最近最少使用）LFU（最常用）FIFO（先入先出）
	 * reference 包含在高速缓存对象的引用的类型。可能的值有：strong soft
	 */
	private CacheConfig getConfig(RCacheConfig config) {
		if (config.getTtl() > 0) {
			this.ttl = config.getTtl() * 1000 / 2;
		}
		if (config.getMaxIdleTime() > 0) {
			this.idleTime = config.getMaxIdleTime() * 1000 / 2;
		}
		if (reference == null) {
			reference = "strong";
		}
		if (algorithm == null) {
			algorithm = "LRU";
		}
		if (name == null) {
			name = getCacheType() + "_cacheId";
		}
		if (maxMemorySize <= 0) {
			maxMemorySize = 1024 * 1024 * 1024;
		}
		if (maxSize <= 0) {
			maxSize = 10000;
		}
		CacheConfig conf = new CacheConfigImpl(name, null, ttl, idleTime, maxMemorySize, maxSize, null, algorithm, reference);
		return conf;
	}

	private Cache cache(RCacheConfig config) throws CacheException {
		CacheConfig conf = getConfig(config);
		Cache cache = getCache(conf);
		return cache;
	}

	public abstract Cache getCache(CacheConfig conf) throws CacheException;

	public abstract String getCacheType();

	public void replace(Object objId, Object obj) {
		try {
			cache.put(objId, obj);
		} catch (CacheException e) {
			logger.error("--[" + getCacheType() + "]cache(" + name + ") replace error!", e);
		}
	}

	public void put(Object objId, Object obj) {
		try {
			cache.put(objId, obj);
		} catch (CacheException e) {
			logger.error("--[" + getCacheType() + "]cache(" + name + ") put error!", e);
		}
	}

	public Object get(Object objId) {
		try {
			String info = cache.getCacheInfo().toString();
			System.out.println("--------{name:" + name + ",ttl:" + StringUtils.time(0, ttl) + ",idleTime:" + StringUtils.time(0, idleTime) + ",reference:" + reference + ",algorithm:" + algorithm + ",maxMemorySize:" + StringUtils.capacity(maxMemorySize, false) + ",maxSize:" + maxSize + ",info:[" + info + "]}-------------------------------------");
			Object obj = cache.get(objId);
			if (obj != null) {
				if ("none".equals(obj.toString())) {
					return null;
				}
				return obj;
			} else {
				cache.put(objId, "none");
			}
			return obj;
		} catch (CacheException e) {
			logger.error("--[" + getCacheType() + "]cache(" + name + ") get error!", e);
		}
		return null;
	}

	public void putInLocal(Object objId, Object obj) {
		try {
			cache.put(objId, obj);
		} catch (CacheException e) {
			logger.error("--[" + getCacheType() + "]cache(" + name + ") putInLocal error!", e);
		}
	}

	public Object getInLocal(Object objId) {
		try {
			return cache.get(objId);
		} catch (CacheException e) {
			logger.error("--[" + getCacheType() + "]cache(" + name + ") getInLocal error!", e);
		}
		return null;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setMaxMemorySize(long maxMemorySize) {
		this.maxMemorySize = maxMemorySize;
	}

	public void setTtl(long ttl) {
		this.ttl = ttl;
	}

	public void setIdleTime(long idleTime) {
		this.idleTime = idleTime;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
