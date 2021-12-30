package com.yjl.rabbit.basic.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@Configuration
public class WorkQueueConfig {


    @Bean
    public Queue WorkQueue() {
        return new Queue("work.queue");
    }

}
