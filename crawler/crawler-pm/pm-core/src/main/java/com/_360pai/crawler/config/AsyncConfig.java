package com._360pai.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述：线程池配置
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/16 14:27
 */
@Configuration
public class AsyncConfig {

    private int corePoolSize = 5;//线程池维护线程的最少数量

    private int maxPoolSize = 30;//线程池维护线程的最大数量

    private int queueCapacity = 5; //缓存队列

    private String threadNamePrefix = "aliZc-";//线程前缀
    /**
     *
     * 阿里拍卖资产列表
     * @param
     */
    @Bean(name = "aliZcListExecutor")
    public Executor aliZcListExecutor() {
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//对拒绝task的处理策略
        executor.setKeepAliveSeconds(60);
        executor.initialize();
        return executor;
    }






    /**
     *
     * 阿里拍卖资产列表
     * @param
     */
    @Bean(name = "alisFListExecutor")
    public Executor alisFListExecutor() {
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("sfList-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//对拒绝task的处理策略
        executor.setKeepAliveSeconds(60);
        executor.initialize();
        return executor;
    }
}
