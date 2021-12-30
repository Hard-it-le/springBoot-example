package com.yjl.rabbit.test.advanced;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PersistentTest {


    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    public void doSendMessage() {
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            //设置消息持久化
            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        };

        String message = "生产者发送消息持久化";

        rabbitTemplate.convertAndSend("ex.ps", "ps", message.getBytes(),
                messagePostProcessor);

    }
}
