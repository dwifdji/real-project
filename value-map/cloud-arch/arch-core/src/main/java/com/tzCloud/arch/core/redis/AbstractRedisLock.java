package com.tzCloud.arch.core.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author RuQ
 * @Title: AbstractRedisLock
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/11 12:57
 */
public abstract class AbstractRedisLock {


    public static final String REDIS_KEY_PRE = "DD_LOCK_";

    private String redisKey = "";

    private int timeout = 0;

    private RedisTemplate redisTemplate;

    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractRedisLock.class);

    public  AbstractRedisLock(){}

    public AbstractRedisLock(String redisKey,int timeout,RedisTemplate redisTemplate){
        this.redisKey = redisKey;
        this.timeout = timeout;
        this.redisTemplate =redisTemplate;
    }

    abstract public void dealBusiness();

    public  void  redisLock (){
        long count = redisTemplate.opsForValue().increment(REDIS_KEY_PRE+redisKey, 1);
        if(count == 1){
            try {
                dealBusiness();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOGGER.info("{} doing business...",redisKey);
            redisTemplate.expire(REDIS_KEY_PRE+redisKey, timeout, TimeUnit.SECONDS);
        }else {
            LOGGER.error("{} is locking,please wait",redisKey);
        }
    }

}
