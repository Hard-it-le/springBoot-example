package com.yjl.rabbit.advanced.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 * <p>
 * 消息持久化
 */
@Configuration
public class PersistentConfig {

    @Bean
    public Queue PersistentQueue() {
        return new Queue("queue.ps", true, false, false, null);
    }


    @Bean
    public Exchange PersistentExchange() {
        Exchange exchange = new
                DirectExchange("ex.ps",
                true,
                false,
                null);
        return exchange;
    }

    @Bean
    public Binding PersistentBinding() {
        return BindingBuilder
                .bind(PersistentQueue())
                .to(PersistentExchange())
                .with("ps")
                .noargs();
    }
}
