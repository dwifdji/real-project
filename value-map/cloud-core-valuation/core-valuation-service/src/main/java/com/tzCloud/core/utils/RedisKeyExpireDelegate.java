package com.tzCloud.core.utils;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 * @author RuQ
 * @Title: RedisKeyExpireDelegate
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/10 18:54
 */
@Service
public  class RedisKeyExpireDelegate implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("channel:" + new String(message.getChannel())
                + ",message:" + new String(message.getBody()));

    }
}
