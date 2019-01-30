package com.dev.share.cache4j;
/**
 * <pre>
 * Created with IntelliJ IDEA.
 * User: lwb
 * Date: 2018/5/17
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 * </pre>
 *
 * @author liwen
 */
public class RCacheConfig {


    /*缓存名称*/
    private String cacheName;


    private String cacheLock;


    /*ttl value*/
    private int ttl;


    /*最大空闲时间*/
    private int maxIdleTime;


    /*最大数量*/
    private int maxSize;



    public RCacheConfig(String cacheName, String cacheLock , int ttl, int maxIdleTime, int maxSize) {
       this(cacheName , cacheLock , ttl , maxIdleTime);
       this.maxSize  = maxSize;
    }


    public RCacheConfig(String cacheName, String cacheLock , int ttl, int maxIdleTime) {
        this.cacheName = cacheName;
        this.cacheLock = cacheLock;
        this.ttl = ttl;
        this.maxIdleTime = maxIdleTime;
    }


    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }


    public String getCacheLock() {
        return cacheLock;
    }

    public void setCacheLock(String cacheLock) {
        this.cacheLock = cacheLock;
    }
}