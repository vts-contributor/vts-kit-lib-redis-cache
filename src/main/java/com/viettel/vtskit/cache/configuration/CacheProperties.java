package com.viettel.vtskit.cache.configuration;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

public class CacheProperties extends RedisProperties {

    /**
     * Validate properties at here if necessary
     */
    private void validateProperties(){

    }

    @PostConstruct
    void init(){
        validateProperties();
    }

}
