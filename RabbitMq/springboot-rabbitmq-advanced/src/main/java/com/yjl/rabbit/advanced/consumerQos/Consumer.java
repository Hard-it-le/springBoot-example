package com.yjl.rabbit.advanced.consumerQos;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 * <p>
 * 消费端限流
 */
@Configuration
public class Consumer {

    @Bean
    public RabbitListenerContainerFactory
    rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        //SimpleRabbitListenerContainerFactory发现消息中
        // 有 content_type有text 就会默认将其 转换为String 类型的，
        // 没有 content_type 都按 byte[]类型
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置并发线程数
        factory.setConcurrentConsumers(10);
        //设置最大并发线程数
        factory.setMaxConcurrentConsumers(20);
        return factory;
    }


}
