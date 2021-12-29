package com.yjl.rabbit.consumers.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/29
 * 消费者监听
 */
@Configuration
public class RabbitMqListener {

    /**
     * 定义队列的监听方法，RabbitListener表示监听哪一个队列
     *
     * @param message
     */
    @RabbitListener(queues = "boot_queue")
    public void ListenerQueue(Message message) {
        System.out.println("message:" + message);
    }
}
