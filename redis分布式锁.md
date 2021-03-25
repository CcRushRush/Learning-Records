### 分布式锁的两种方式
1. redis setnx
2. redisson

### 实现方式
~~~xml
<!-- https://mvnrepository.com/artifact/org.redisson/redisson-spring-boot-starter -->
        <dependency>
            <groupId>org.redisson</groupId>
            <artifactId>redisson-spring-boot-starter</artifactId>
            <version>3.15.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
~~~

~~~java
 package com.lc.redislock.util;

import ch.qos.logback.core.util.TimeUtil;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author cc
 * @version 1.0
 * @date 2021/3/24 18:09
 */

/**
 * 分布式锁，两个方案
 * 方案一：redis setnx
 * 方案二：redisson
 */

@Component
public class RedisLockUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private Redisson redisson;


    /**
     * 上分布式锁 redis --- setnx
     *
     * @param key      锁的key
     * @param timeout  key的最长存活时间， 超时自动删除key，避免产生死锁
     * @param timeUnit 超时时间单位
     * @return
     */
    public boolean tryLock(String key, long timeout, TimeUnit timeUnit) {
        String uuid = UUID.randomUUID().toString();
        //保证值是唯一
        threadLocal.set(uuid);
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(key, uuid, timeout, timeUnit);
        if (lock == null)
            lock = false;
        return lock;
    }

    /**
     * 释放锁
     *
     * @param key
     */
    public void releaseLock(String key) {
        //该判断避免误删
        if (threadLocal.get().equals(stringRedisTemplate.opsForValue().get(key))) {
            stringRedisTemplate.delete(key);
        }
    }

    public void lockRedisson(String key) {
        RLock rLock = redisson.getLock(key);
        rLock.lock();
    }

    public void unLockRedisson(String key) {
        RLock rLock = redisson.getLock(key);
        rLock.unlock();
    }
}

~~~
