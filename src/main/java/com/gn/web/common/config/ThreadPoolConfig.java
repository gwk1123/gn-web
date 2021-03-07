package com.gn.web.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author ruoyi
 **/
@Configuration
@EnableAsync
@EnableScheduling
public class ThreadPoolConfig
{
    // 核心线程池大小
    private int corePoolSize = 80;

    // 最大可创建的线程数
    private int maxPoolSize = 100;

    // 队列最大长度
    private int queueCapacity = 100000;

    private String poolName = "pool_name_";

    // 线程池维护线程所允许的空闲时间
//    private int keepAliveSeconds = 300;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(poolName);
//        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }



    private final String ASYNC_POOL_1 = "asyncExecutor1";
    private final String ASYNC_POOL_NAME_1 = "async_executor1_";

    private final String ASYNC_POOL_2 = "asyncExecutor2";
    private final String ASYNC_POOL_NAME_2 = "async_executor2_";

    private final String ASYNC_POOL_3 = "asyncExecutor3";
    private final String ASYNC_POOL_NAME_3 = "async_executor3_";

    private final String ASYNC_POOL_4 = "asyncExecutor4";
    private final String ASYNC_POOL_NAME_4 = "async_executor4_";

    private final String ASYNC_POOL_5 = "asyncExecutor5";
    private final String ASYNC_POOL_NAME_5 = "async_executor5_";

    private final String ASYNC_POOL_6 = "asyncExecutor6";
    private final String ASYNC_POOL_NAME_6 = "async_executor6_";

    private final String ASYNC_POOL_7 = "asyncExecutor7";
    private final String ASYNC_POOL_NAME_7 = "async_executor7_";

    private final String ASYNC_POOL_8 = "asyncExecutor8";
    private final String ASYNC_POOL_NAME_8 = "async_executor8_";

    private final String ASYNC_POOL_9 = "asyncExecutor9";
    private final String ASYNC_POOL_NAME_9 = "async_executor9_";

    private int corePool = 30;

    private int maxPool = 40;

    @Bean(ASYNC_POOL_1)
    public ThreadPoolTaskExecutor getQujqlfxExecutorAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPool);
        executor.setQueueCapacity(20000);
        executor.setThreadNamePrefix(ASYNC_POOL_NAME_1);
        return executor;
    }


    @Bean(ASYNC_POOL_2)
    public ThreadPoolTaskExecutor getQuxkfxExecutorAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPool);
        executor.setQueueCapacity(20000);
        executor.setThreadNamePrefix(ASYNC_POOL_NAME_2);
        return executor;
    }


    @Bean(ASYNC_POOL_3)
    public ThreadPoolTaskExecutor getQuxkttsExecutorAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPool);
        executor.setQueueCapacity(20000);
        executor.setThreadNamePrefix(ASYNC_POOL_NAME_3);
        return executor;
    }

    @Bean(ASYNC_POOL_4)
    public ThreadPoolTaskExecutor getQuxyttsExecutorAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPool);
        executor.setQueueCapacity(20000);
        executor.setThreadNamePrefix(ASYNC_POOL_NAME_4);
        return executor;
    }

    @Bean(ASYNC_POOL_5)
    public ThreadPoolTaskExecutor getCtmlptExecutorAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPool);
        executor.setQueueCapacity(20000);
        executor.setThreadNamePrefix(ASYNC_POOL_NAME_5);
        return executor;
    }

    @Bean(ASYNC_POOL_6)
    public ThreadPoolTaskExecutor getExecutorAsync6() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPool);
        executor.setQueueCapacity(20000);
        executor.setThreadNamePrefix(ASYNC_POOL_NAME_6);
        return executor;
    }

    @Bean(ASYNC_POOL_7)
    public ThreadPoolTaskExecutor getExecutorAsync7() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPool);
        executor.setQueueCapacity(20000);
        executor.setThreadNamePrefix(ASYNC_POOL_NAME_7);
        return executor;
    }

    @Bean(ASYNC_POOL_8)
    public ThreadPoolTaskExecutor getExecutorAsync8() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPool);
        executor.setQueueCapacity(20000);
        executor.setThreadNamePrefix(ASYNC_POOL_NAME_8);
        return executor;
    }

    @Bean(ASYNC_POOL_9)
    public ThreadPoolTaskExecutor getExecutorAsync9() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPool);
        executor.setQueueCapacity(20000);
        executor.setThreadNamePrefix(ASYNC_POOL_NAME_9);
        return executor;
    }


}
