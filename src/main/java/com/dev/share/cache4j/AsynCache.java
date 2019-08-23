//package com.dev.share.cache4j;
//
//import net.sf.cache4j.Cache;
//import net.sf.cache4j.CacheConfig;
//import net.sf.cache4j.CacheException;
//import net.sf.cache4j.impl.UnSynchronizedCache;
//
///***
// * @project emgw-core
// * @description 异步二级缓存
//  * 作者:ZhangYi
//  * 时间:2019年1月3日 下午2:52:08
//  * 版本:v1.0
//  * JDK:1.8.192
//  * @company 空间畅想
//  */
//public class AsynCache extends AbstractCache {
//	private static final long serialVersionUID = 1830588774254918495L;
//	private static final String CACHE_TYPE = "ASYN";
//	private UnSynchronizedCache cache;
//	@Override
//	public Cache getCache(CacheConfig conf) throws CacheException {
//		cache = new UnSynchronizedCache();
//		cache.setCacheConfig(conf);
//		return cache;
//	}
//	@Override
//	public String getCacheType() {
//		return CACHE_TYPE;
//	}
//	
//	public void clean() throws CacheException {
//		if(cache!=null) {
//			cache.clear(); 
//		}
//	}
//	public UnSynchronizedCache getCache() {
//		return cache;
//	}
//}
