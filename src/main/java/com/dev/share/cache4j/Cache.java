package com.dev.share.cache4j;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import cn.com.kjcx.emgw.cache.DistributedCache;
//import cn.com.kjcx.emgw.cache.RCacheConfig;
//import net.sf.cache4j.impl.CacheConfigImpl;
import net.sf.cache4j.CacheConfig;
import net.sf.cache4j.CacheException;
import net.sf.cache4j.impl.SynchronizedCache;

/**
 * 同步二级缓存
 * 
 * @company 空间畅想
 * 
 */
public class Cache extends AbstractCache {
	private static final long serialVersionUID = -1273968166845884894L;
//	private static final Logger log = LoggerFactory.getLogger(Cache.class);
	private static final String CACHE_TYPE = "SYN";
//	// 缓存的名称
//	private String name = "";
//	// 最大生存期，0 - 没有限制
//	// 最大数量
//	private int maxSize = 10000;
//	// 最大内存
//	private long maxMemorySize = 1024 * 1024 * 1024;
//	// 移除对象的算法。可能的值有：LRU（最近最少使用）LFU（最常用）FIFO（先入先出）
//	private String algorithm = "LRU";
	// ------------------------------------------
	// cache4j
	private transient SynchronizedCache cacheImpl;
//	private DistributedCache distributedCache;
//
//	public void init_dependence() {
//		try {
//			if (distributedCache == null) {
//				distributedCache = SpringContextHelper.getBean(DistributedCache.class);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		if (distributedCache == null) {
//			throw new IllegalArgumentException("distributedCache constructor error~");
//		}
//		RCacheConfig config = distributedCache.getConfig(name);
//
//		if (cacheImpl == null) {
//			CacheConfig cacheConfig = new CacheConfigImpl(name, null, config.getTtl() * 500,
//					config.getMaxIdleTime() * 500, maxMemorySize, maxSize, null, algorithm, "strong");
//			SynchronizedCache cache = new SynchronizedCache();
//			try {
//				cache.setCacheConfig(cacheConfig);
//				cacheImpl = cache;
//			} catch (CacheException e) {
//				e.printStackTrace();
//			}
//		}
//		if (cacheImpl == null) {
//			throw new IllegalArgumentException("cacheImpl constructor error~");
//		}
//	}

	@Override
	public net.sf.cache4j.Cache getCache(CacheConfig conf) throws CacheException {
		cacheImpl = new SynchronizedCache();
		cacheImpl.setCacheConfig(conf);
		return cacheImpl;
	}
	@Override
	public String getCacheType() {
		return CACHE_TYPE;
	}
	public void clean() throws CacheException {
		if(cacheImpl!=null) {
			cacheImpl.clear(); 
		}
	}
	public SynchronizedCache getCache() {
		return cacheImpl;
	}
//	public void replace(Object objId, Object obj) {
//		log.debug("replace distributedCache name = {}", name);
//		distributedCache.replace(name, objId, obj);
//		cacheImpl.put(objId, obj);
//	}
//
//	public void put(Object objId, Object obj) {
//		log.debug("put distributedCache name = {}", name);
//		distributedCache.put(name, objId, obj);
//		cacheImpl.put(objId, obj);
//		
//	}
//
//	public Object get(Object objId) {
//		log.debug("get distributedCache name = {}", name);
//		
//		Object obj = cacheImpl.get(objId);
//		if (obj != null) {
//			if("none".equals(obj.toString())){
//				return null;
//			}
//			return obj;
//		}
//		obj = distributedCache.get(name, objId);
//		if (obj != null) {
//			cacheImpl.put(objId, obj);
//		}else{
//			cacheImpl.put(objId, "none");
//		}
//		return obj;
//	}
//
//	
//	public void putInLocal(Object objId, Object obj) {
//		cacheImpl.put(objId, obj);
//	}
//
//	public Object getInLocal(Object objId) {
//		return cacheImpl.get(objId);
//	}
//	
//	public void setName(String name) {
//
//		this.name = name;
//	}
//
//	public void setMaxSize(int maxSize) {
//		this.maxSize = maxSize;
//	}
//
//	public void setAlgorithm(String algorithm) {
//		this.algorithm = algorithm;
//	}
//
//	public void setMaxMemorySize(long maxMemorySize) {
//		this.maxMemorySize = maxMemorySize;
//	}
}
