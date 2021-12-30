package com.yjl.rabbit.test.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkTest {


    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    public void testSendMessage() {
        String message = "hello-";
        for (int i = 0; i < 20; i++) {
            message = message + 1;

            rabbitTemplate.convertAndSend("work.queue",
                     message.getBytes());
        }
        System.out.println("发送成功");
    }

}