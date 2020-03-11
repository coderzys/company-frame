package com.coder.lesson.shiro;

import com.coder.lesson.service.RedisService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @类名 RedisCacheManager
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/19 17:47
 * @版本 1.0
 **/
public class RedisCacheManager implements CacheManager {
    @Autowired
    private RedisService redisService;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<>(s, redisService);
    }
}
