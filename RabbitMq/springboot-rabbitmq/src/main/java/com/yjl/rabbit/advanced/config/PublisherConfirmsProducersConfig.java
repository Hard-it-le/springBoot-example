package com.yjl.rabbit.advanced.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@Configuration
public class PublisherConfirmsProducersConfig {


    public Queue PublisherConfirms() {
        return new Queue("queue.biz", false, false, false, null);
    }


    public Exchange PublisherExchange() {
        Exchange exchange = new
                DirectExchange("ex.biz",
                false,
                false,
                null);
        return exchange;
    }


    public Binding PublisherBinding() {
        return BindingBuilder
                .bind(PublisherConfirms())
                .to(PublisherExchange())
                .with("biz")
                .noargs();
    }
}
