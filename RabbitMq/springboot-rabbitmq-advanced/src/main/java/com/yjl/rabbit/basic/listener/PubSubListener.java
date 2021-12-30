package com.yjl.rabbit.basic.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 * <p>
 * pub/sub模式进行消息监听
 */
@Component
public class PubSubListener {

    @RabbitListener(queues = "pub.queue01")
    public void PubSubListener01(String message) {

        System.out.println("发布订阅模式1received message : " + message);
    }

    @RabbitListener(queues = "pub.queue02")
    public void PubSubListener02(String message) {

        System.out.println("发布订阅模式2 received message : " + message);
    }
}
