package com.yjl.rabbit.advanced.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 * <p>
 * TTL，Time to Live的简称， 即过期时间。
 */
@Configuration
public class TTLConfig {

    @Bean
    public Queue queueTTLWaiting() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("x-message-ttl", 10000);
        Queue queue = new Queue("ttl.queue", false, false, false, map);
        return queue;
    }

    @Bean
    public Queue queueWaiting() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("x-message-ttl", 10000);
        Queue queue = new Queue("waiting.queue", false, false, false);
        return queue;
    }

    @Bean
    public Exchange exchangeTTLWaiting() {
        TopicExchange exchange = new TopicExchange("ttl.ex", false, false);
        return exchange;
    }


    @Bean
    public Exchange exchangeWaiting() {
        TopicExchange exchange = new TopicExchange("waiting.ex", false, false);
        return exchange;
    }


    @Bean
    public Binding bindingTTLWaiting() {
        return BindingBuilder.bind(queueTTLWaiting()).to(exchangeTTLWaiting()).with("ttl.*").noargs();
    }

    @Bean
    public Binding bindingWaiting() {
        return BindingBuilder.bind(queueWaiting()).to(exchangeWaiting()).with("waiting.*").noargs();
    }
}
