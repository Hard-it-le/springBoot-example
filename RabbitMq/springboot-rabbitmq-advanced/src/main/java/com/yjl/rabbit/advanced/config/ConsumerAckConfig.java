package com.yjl.rabbit.advanced.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 * <p>
 * 消费端确认消息
 */
@Configuration
public class ConsumerAckConfig {


    @Bean
    public Queue AckQueue() {
        return new Queue("ack.queue", false, false, false, null);
    }


    @Bean
    public Exchange AckExchange() {
        Exchange topicExchange = new TopicExchange("ack.ex", false, false, null);


        return topicExchange;
    }

    @Bean
    public Binding AckBinding() {
        return BindingBuilder
                .bind(AckQueue())
                .to(AckExchange())
                .with("ack.*")
                .noargs();
    }


}
