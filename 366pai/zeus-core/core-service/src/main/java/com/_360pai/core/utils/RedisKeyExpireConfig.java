package com._360pai.core.utils;

import com._360pai.core.aspact.RedisKeyExpireDeal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author RuQ
 * @Title: RedisKeyExpireConfig
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/10 20:14
 */
@Configuration
public class RedisKeyExpireConfig {

    @Bean
    public MessageListenerAdapter messageListenerAdapter(RedisKeyExpireDeal redisKeyExpireDeal){
        return new MessageListenerAdapter(redisKeyExpireDeal);
    }

    @Bean
    public RedisMessageListenerContainer listenerContainer(JedisConnectionFactory getConnectionFactory , MessageListenerAdapter messageListenerAdapter){

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(getConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic("__key*__:expired"));
        return container;
    }
}
