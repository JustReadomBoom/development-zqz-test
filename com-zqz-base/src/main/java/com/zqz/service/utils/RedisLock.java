package com.zqz.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zqz
 * @Description:
 * @Date:
 */
@Component
public class RedisLock {
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
            "    return redis.call(\"del\",KEYS[1])\n" +
            "else\n" +
            "    return 0\n" +
            "end";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Lua脚本
     */
    public static final DefaultRedisScript<Long> REDIS_SCRIPT = new DefaultRedisScript<>(RELEASE_LOCK_SCRIPT, Long.class);




    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
     * 对于 Redis 集群则无法使用
     * 支持重复，线程安全
     *
     * @param lockKey 加锁键
     * @param value   加锁客户端唯一标识(采用UUID)
     * @param seconds 锁过期时间
     * @return
     */
    public boolean tryLock(String lockKey, String value, long seconds) {
        Boolean ifAbsent = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, value, seconds, TimeUnit.SECONDS);
        if (null != ifAbsent && ifAbsent) {
            return true;
        }
        return false;
    }

    /**
     * 与 tryLock 相对应，用作释放锁
     *
     * @param lockKey
     * @param value
     * @return
     */
    public boolean releaseLock(String lockKey, String value) {
        stringRedisTemplate.execute(REDIS_SCRIPT, Collections.singletonList(lockKey), value);
        return true;
    }
}
