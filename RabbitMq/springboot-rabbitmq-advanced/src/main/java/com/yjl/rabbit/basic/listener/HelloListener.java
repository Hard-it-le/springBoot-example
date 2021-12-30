package com.yjl.rabbit.basic.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@Component
public class HelloListener {

    /**
     * 直连模式的多个消费者，会分到其中一个消费者进行消费。类似task模式
     * 通过注入RabbitContainerFactory对象，来设置一些属性，
     * 相当于task里的channel.basicQos
     *
     * @param message
     */
    @RabbitListener(queues = "hello.queue")
    public void HelloListener(String message) {
        System.out.println("helloWorld模式 received message : " + message);
    }
}
