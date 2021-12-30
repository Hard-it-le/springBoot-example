package com.yjl.rabbit.test.basic;

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
public class RoutingTest {
    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    public void testSendMessage() throws UnsupportedEncodingException {
        String routingKey = "";
        String message = "routing";
        if (null == routingKey || "" == routingKey) {
            routingKey = "china.shanghai";
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send("routing.ex", routingKey, new Message(message.getBytes("UTF-8"), messageProperties));
    }


}
