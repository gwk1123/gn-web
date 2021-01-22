package com.gn.web.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 * @author ruoyi
 **/
@Component
public class RedisCache {
    public final RedisTemplate redisTemplate;

    public RedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * String类型存贮
     *
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void saveString(String key, Object value, Integer timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public Object getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteKey(String key){
        redisTemplate.delete(key);
    }
}
