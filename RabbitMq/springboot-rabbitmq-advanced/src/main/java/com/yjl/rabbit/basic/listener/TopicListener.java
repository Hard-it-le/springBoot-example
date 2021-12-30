package com.yjl.rabbit.basic.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 * <p>
 * 注意这个模式会有优先匹配原则。例如发送routingKey=hunan.IT,那匹配到hunan.*(hunan.IT,hunan.eco),之后就不会再去匹配*.ITd
 */
@Component(value = "topic")
public class TopicListener {

    @RabbitListener(queues = "topic01.queue")
    public void topicListener1(String message) {
        System.out.println("Topic模式 topic01.queue received message : " + message);
    }

    @RabbitListener(queues = "topic02.queue")
    public void topicListener2(String message) {
        System.out.println("Topic模式 topic01.queue received  message : " + message);
    }
}
