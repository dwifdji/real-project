package com._360pai.arch.core.redis;


import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Autowired
    private SystemProperties systemProperties;

    @Bean
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        System.out.println(config.getMaxWaitMillis());
        return config;
    }

    @Bean
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        factory.setHostName(systemProperties.getRedisHost());
        factory.setPort(systemProperties.getRedisPort());
        factory.setDatabase(systemProperties.getRedisDbIndex());
        factory.setPassword(systemProperties.getRedisPassword());
        logger.info("JedisConnectionFactory bean init success.");
        return factory;
    }

    @Bean
    public RedisTemplate<String, ?> getRedisTemplate() {
        RedisTemplate<String, ?> template = new RedisTemplate();
        template.setConnectionFactory(getConnectionFactory());

        return template;
    }

    @Bean
    public RedisTemplate<String, Serializable> stringRedisTemplate() {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getConnectionFactory());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public RedisCachemanager redisCachemanager() {
        DefaltRedisCachemanager defaultRedisCacheManager = new DefaltRedisCachemanager();
        defaultRedisCacheManager.setRedisTemplate(stringRedisTemplate());
        return defaultRedisCacheManager;
    }
}
