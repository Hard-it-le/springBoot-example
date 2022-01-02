package com.yjl.rabbit.basic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@Configuration
public class RoutingConfig {


    @Bean
    public Queue routingQueue01() {
        return new Queue("route01.queue", false, false, false, null);
    }


    @Bean
    public Queue routingQueue02() {
        return new Queue("route02.queue", false, false, false, null);
    }


    @Bean
    public DirectExchange routingExchange() {
        return new DirectExchange("routing.ex");
    }


    @Bean
    public Binding routingBinding01() {
        return BindingBuilder.bind(routingQueue01()).to(routingExchange()).with("china.shanghai");
    }

    @Bean
    public Binding routingBinding02() {
        return BindingBuilder.bind(routingQueue02()).to(routingExchange()).with("china.beijing");
    }
}
