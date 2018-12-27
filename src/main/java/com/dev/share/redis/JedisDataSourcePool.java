package com.dev.share.redis;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

/**
 * redis连接资源池
 * 
 * @company 空间畅想
 * 
 */
public class JedisDataSourcePool {
	// ----------------------内部使用变量
	// 资源配置
	private HashMap<String, String> redisSourceUrlMap;
	// 链接池
	private HashMap<String, JedisSentinelPool> internalPool = new HashMap<String, JedisSentinelPool>();


	/**
	 * 获取连接资源
	 * 
	 * @param key
	 *            key
	 * @return
	 */
	public JedisSentinelPool getJedisPool(String key) {
		JedisSentinelPool jedisPool = internalPool.get(key);
		if (jedisPool == null) {
			synchronized (this) {
				jedisPool = internalPool.get(key);
				if (jedisPool == null) {
					String sourceURL = redisSourceUrlMap.get(key);
					jedisPool = createJedisSentinelPool(key, sourceURL);
					internalPool.put(key, jedisPool);
				}
			}
		}
		return jedisPool;
	}

	/**
	 * 获取连接资源
	 * 
	 * @param key
	 *            key
	 * @return
	 */
	public JedisSentinelPool getJedisPool(String key, String defaultKey) {
		JedisSentinelPool jedisPool = internalPool.get(key);
		if (jedisPool == null) {
			synchronized (this) {
				jedisPool = internalPool.get(key);
				if (jedisPool == null) {
					String sourceURL = redisSourceUrlMap.get(key);
					if (sourceURL == null) {
						sourceURL = redisSourceUrlMap.get(defaultKey);
					}
					jedisPool = createJedisSentinelPool(key, sourceURL);
					internalPool.put(key, jedisPool);
				}
			}
		}
		return jedisPool;
	}

	/**
	 * 创建
	 * 
	 * @param host
	 * @return
	 
	private JedisPool createJedisPool(String key, String host) {
		URI uri = URI.create(host);
		if (uri.getScheme() != null && uri.getScheme().equals("redis")) {
			String h = uri.getHost();
			int port = uri.getPort();
			String password = uri.getUserInfo().split(":", 2)[1];
			int database = Integer.parseInt(uri.getPath().split("/", 2)[1]);
			return new JedisPool(getJedisPoolConfig(), h, port, 10000, password, database, key);
		} else {
			return null;
		}
	}
	*/
	
	/*******************************************************************************************************************/
	static class SentinelInfo{
		HashSet<String> auth = new HashSet<String>();
		int database;
		int timeout = 1000;
		String type;
		String name;
		String password;
		String [] otherIP;
		private SentinelInfo(String info){
			URI uri = URI.create(info);
			this.type = uri.getScheme();
			this.auth.add(uri.getAuthority());
			String [] baseInfo = uri.getPath().split("/");
			this.database = Integer.valueOf(baseInfo[1]);
			this.password = baseInfo[2];
			this.name = baseInfo[3];
			this.otherIP = uri.getQuery().split(",");
			if(otherIP.length >0){
				for(String ip: otherIP){
					this.auth.add(ip);
				}
			}	
			System.out.println( auth );
		}
		public static SentinelInfo parse(String info){
			return new SentinelInfo(info);
		}
	}
	public JedisSentinelPool createJedisSentinelPool(String key , String host){
		SentinelInfo sentinel = SentinelInfo.parse(host);
		if(sentinel.type != null && sentinel.type.equals("redis")){
			return new JedisSentinelPool(sentinel.name,sentinel.auth, getJedisPoolConfig(), sentinel.timeout, sentinel.password, sentinel.database, key);
		}else{
			return null;
		}
	}
	
	/***************************************************************************************************************************************/
	/**
	 * getJedisPoolConfig
	 * 
	 * @return
	 */
	private JedisPoolConfig getJedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(500);
		config.setMinIdle(100);
		config.setMaxWaitMillis(90 * 1000);
		config.setMaxTotal(2000);
		config.setBlockWhenExhausted(true);
		config.setTestWhileIdle(true);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		config.setTestOnCreate(true);
		config.setMinEvictableIdleTimeMillis(30 * 1000);
		config.setTimeBetweenEvictionRunsMillis(10 * 1000);
		config.setNumTestsPerEvictionRun(3);
		config.setTestWhileIdle(true);
		return config;
	}
}
