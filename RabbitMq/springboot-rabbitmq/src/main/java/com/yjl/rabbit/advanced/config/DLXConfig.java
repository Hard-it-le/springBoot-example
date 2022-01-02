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
 * 死信队列
 */
@Configuration
public class DLXConfig {


    @Bean
    public Queue queue() {
        HashMap<String, Object> map = new HashMap<>();
//消息的生存时间 10s
        map.put("x-message-ttl", 10000);
//设置该队列所关联的死信交换器（当队列消息         TTL到期后依然没有消费，则加入死信队列）
        map.put("x-dead-letter-exchange", "ex.go.dlx");
//设置该队列所关联的死信交换器的routingKey，如果没有特殊指定，使用原队列的routingKey
        map.put("x-dead-letter-routing-key", "go.dlx");
        Queue queue = new Queue("queue.go", true, false, false, map);
        return queue;
    }


    @Bean
    public Queue queueDlx() {

        Queue queue = new Queue("queue.go.dlx", true, false, false);
        return queue;
    }

    @Bean
    public Exchange exchange() {
        TopicExchange exchange = new TopicExchange("ex.go", true, false);
        return exchange;
    }


    @Bean
    public Exchange exchangeDlx() {
        TopicExchange exchange = new TopicExchange("ex.go.dlx", true, false);
        return exchange;
    }


    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("ex.go.*").noargs();
    }

    @Bean
    public Binding bindingDlx() {
        return BindingBuilder.bind(queueDlx()).to(exchangeDlx()).with("*.dlx").noargs();
    }
}
