package com.yjl.rabbit.test.advanced;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
public class TTLTest {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    public void sendMessage() {
        String message = "发送了ttl消息";
        rabbitTemplate.convertAndSend("ttl.ex", "ttl.*", message.getBytes());
        System.out.println(message + "发送成功");
    }


    @Test
    public void sendMessageTo() throws UnsupportedEncodingException {
        MessageProperties properties = new
                MessageProperties();
        properties.setExpiration("5000");
        Message message = new Message(" 发送了 WAITINg MESSAGE".getBytes("utf-8"), properties);
        rabbitTemplate.convertAndSend("waiting.ex", "waiting.*", message);
        System.out.println(message+"waiting消息发送成功");
    }
}
