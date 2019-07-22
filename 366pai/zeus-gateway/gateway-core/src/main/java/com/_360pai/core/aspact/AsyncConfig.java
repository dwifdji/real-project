package com._360pai.core.aspact;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述：异步线程池配置
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/16 14:27
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    private int corePoolSize = 2;//线程池维护线程的最少数量

    private int maxPoolSize = 10;//线程池维护线程的最大数量

    private int queueCapacity = 2; //缓存队列

    private int keepAlive = 60;//允许的空闲时间

    private String threadNamePrefix = "AutoSign-";//线程前缀
    /**
     *
     * 自动签章异步线程池
     * @param
     */
    @Bean(name = "autoSignExecutor")
    public Executor autoSignExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//对拒绝task的处理策略
        executor.setKeepAliveSeconds(keepAlive);
        executor.initialize();
        return executor;
    }
}
