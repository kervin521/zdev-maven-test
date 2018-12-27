package com.dev.share.test;

import com.dev.share.redis.JedisDataSourcePool;

import redis.clients.jedis.Jedis;

public class RedisTest {

	public static void main(String[] args) {
		String url = "redis://10.10.1.189:16377/0/eums/mymaster?10.10.1.189:26378,10.10.1.189:36377";
		Jedis jedis = new JedisDataSourcePool().getJedisPool("test-dev").getResource();
//		jedis.rpush(key, strings);
	}

}
