package com.yjl.rabbit.basic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 * <p>
 * HelloWorld
 * * 直连模式只需要声明队列，所有消息都通过队列转发。
 * * 无需设置交换机
 */
@Configuration
public class HelloWorldConfig {


    /**
     * 设置队列
     *
     * @return
     */
    @Bean
    public Queue HelloQueue() {
        return new Queue("hello.queue");
    }

    /**
     * 设置交换机
     *
     * @return
     */
    @Bean
    public DirectExchange HelloExchange() {
        return new DirectExchange("hello.ex", false, false, null);
    }

    @Bean
    public Binding HelloBinding() {
        /**
         * destination:绑定的目的地
         * destinationType:绑定的类型,到交换器还是到队列
         * exchange:交换器名称
         * routingKey:路由key
         * arguments：绑定的属性
         */
        return new Binding(
                "hello.queue",
                Binding.DestinationType.QUEUE,
                "hello.ex",
                "hello.key",
                null);
    }

}
