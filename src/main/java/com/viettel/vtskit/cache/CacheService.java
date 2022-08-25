package com.viettel.vtskit.cache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


public class CacheService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    javax.cache.CacheManager cacheManager;


    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public boolean save(String key, Object value){
        try{
            redisTemplate.opsForValue().set(key,gson.toJson(value));
            return true;
        }catch (Exception e){
            throw e;
        }
    }
    public boolean update(String key, Object value){
        try{
            redisTemplate.opsForValue().getAndSet(key,gson.toJson(value));
            return true;
        }catch (Exception e){
            throw e;
        }
    }
    public boolean delete(String key){
        try{
            redisTemplate.opsForValue().getAndDelete(key);
            return true;
        }catch (Exception e){
            throw e;
        }
    }

    private Boolean createCache( String cacheName,
                              javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        try{
            javax.cache.Cache<Object, Object> cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            } else {
                cacheManager.createCache(cacheName, jcacheConfiguration);
            }
            return true;
        }catch (Exception e){
            throw e;
        }
    }

    public boolean clearAllCache(){
        try{
            for(String name:cacheManager.getCacheNames()){
                cacheManager.getCache(name).clear();
            }
            return true;
        }catch (Exception e){
            throw e;
        }
    }
    public boolean clearCache(String cacheName){
        try{
            cacheManager.getCache(cacheName).clear();
            return true;
        }catch (Exception e){
            throw e;
        }
    }
}
