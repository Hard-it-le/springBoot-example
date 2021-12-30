package com.yjl.rabbit.test.basic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

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
    public void testSendMessage() throws UnsupportedEncodingException {
        String message = "hello-";
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend("", "work.queue",
                    (message + i).getBytes("utf-8"));
        }
        System.out.println("发送成功");
    }

}