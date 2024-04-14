package com.riverburg.eUniversity.configuration.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // minimum opened threads
        threadPoolTaskExecutor.setCorePoolSize(5);

        // the queue capacity, opens new thread if capacity is full
        threadPoolTaskExecutor.setQueueCapacity(50);

        // count of max threads
        threadPoolTaskExecutor.setMaxPoolSize(10);

        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
