package com.zqz.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * @Author: zqz
 * @Description: Redis配置
 * @Date: Created in 11:52 2022/02/25
 */
@Service
@Slf4j
public class RedisConfig implements InitializingBean, DisposableBean {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        log.info("=======RedisConfig Config Load End=======");
    }

    @Override
    public void destroy() throws Exception {
        log.info("=========RedisConfig Config Shutdown=======");
    }
}
