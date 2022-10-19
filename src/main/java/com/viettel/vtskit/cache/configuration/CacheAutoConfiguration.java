package com.viettel.vtskit.cache.configuration;

import com.viettel.vtskit.cache.CacheService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.cache.configuration.MutableConfiguration;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheAutoConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "redis")
    public RedisProperties redisConfigurationProperties(){
        return new CacheProperties();
    }

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(RedissonClient redissonClient){
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();
        Config config = redissonClient.getConfig();

        return RedissonConfiguration.fromInstance(Redisson.create(config),jcacheConfig);
    }

    @Bean
    public CacheService cacheService(){
        return new CacheService();
    }

}
