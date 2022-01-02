package com.yjl.rabbit.basic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@Configuration
public class PubSubConfig {


    @Bean
    public Queue PubSubQueue01() {
        return new Queue("pub.queue01", false, false, false, null);
    }

    @Bean
    public Queue PubSubQueue02() {
        return new Queue("pub.queue02", false, false, false, null);
    }

    @Bean
    public FanoutExchange PubSubFanoutExchange() {
        return new FanoutExchange("pubSub.ex");
    }


    @Bean
    public Binding bindQ1() {
        return BindingBuilder.bind(PubSubQueue01()).to(PubSubFanoutExchange());
    }

    @Bean
    public Binding bindQ2() {
        return BindingBuilder.bind(PubSubQueue02()).to(PubSubFanoutExchange());
    }
}
