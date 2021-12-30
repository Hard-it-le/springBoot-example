package com.yjl.rabbit.basic.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@Component(value = "routing")
public class RoutingListener {

    @RabbitListener(queues = "route01.queue")
    public void RoutingListener01(String message) {

        System.out.println("Routing路由模式routing  11111 received message : " + message);
    }

    @RabbitListener(queues = "route02.queue")
    public void RoutingListener02(String message) {

        System.out.println("Routing路由模式routing  22222 received message : " + message);
    }
}
