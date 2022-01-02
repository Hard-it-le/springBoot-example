package com.yjl.rabbit.basic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@Configuration
public class TopicConfig {


    @Bean
    public Queue topicQ1() {
        return new Queue("topic01.queue");
    }

    @Bean
    public Queue topicQ2() {
        return new Queue("topic02.queue");
    }


    @Bean
    public TopicExchange setTopicExchange() {
        return new TopicExchange("topic.ex");
    }

    @Bean
    public Binding bindTopicHebei1() {
        return BindingBuilder.bind(topicQ1()).to(setTopicExchange()).with("changsha.*");
    }

    @Bean
    public Binding bindTopicHebei2() {
        return BindingBuilder.bind(topicQ2()).to(setTopicExchange()).with("#.beijing");
    }
}
