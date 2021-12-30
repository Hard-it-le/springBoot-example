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
public class HelloTest {


    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    public void testSendMessage() {
        String message = "hello rabbit ";
        rabbitTemplate.convertAndSend("hello.ex",
                "hello.key", message.getBytes());
    }

}
