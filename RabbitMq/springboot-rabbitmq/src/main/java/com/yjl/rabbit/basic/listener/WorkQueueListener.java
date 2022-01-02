package com.yjl.rabbit.basic.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@Component(value = "work")
public class WorkQueueListener {


    /**
     * 工作队列模式
     *
     * @param message
     */
    @RabbitListener(queues = "work.queue")
    public void WorkQueue01(String message) {
        System.out.println("工作队列模式1 received message : " + message);
    }

    /**
     * 工作队列模式
     *
     * @param message
     */
    @RabbitListener(queues = "work.queue")
    public void WorkQueue02(String message) {
        System.out.println("工作队列模式2 received message : " + message);
    }
}
