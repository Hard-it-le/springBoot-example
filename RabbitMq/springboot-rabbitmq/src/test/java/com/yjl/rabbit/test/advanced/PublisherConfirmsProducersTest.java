package com.yjl.rabbit.test.advanced;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
@SpringBootTest
@RunWith(SpringRunner.class)
public class PublisherConfirmsProducersTest {


    private RabbitTemplate rabbitTemplate;

    @Resource
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback((correlationData, flag, cause) -> {
            if (flag) {
                try {
                    System.out.println("消息确认：" + correlationData.getId() + " " + new
                            String(correlationData.getReturnedMessage().getBody(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(cause);
            }
        });
    }


    @Test
    public void doBiz() throws UnsupportedEncodingException {
        MessageProperties props = new MessageProperties();
        props.setCorrelationId("1234");
        props.setConsumerTag("msg1");
        props.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        props.setContentEncoding("utf-8");
        CorrelationData cd = new CorrelationData();
        cd.setId("msg1");
        cd.setReturnedMessage(
                new Message("这是msg1的响应".getBytes("utf-8"), null));
        Message message = new Message(
                "这是等待确认的消息 ".getBytes("utf-8"), props);
        rabbitTemplate.convertAndSend("ex.biz",
                "biz", message, cd);
    }

    @Test
    public void doBizFalse() throws UnsupportedEncodingException {
        MessageProperties props = new MessageProperties();
        props.setCorrelationId("1234");
        props.setConsumerTag("msg1");
        props.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        props.setContentEncoding("utf-8");
        CorrelationData cd = new CorrelationData();
        cd.setId("msg1");
        cd.setReturnedMessage(new Message(" 这是 msg1 的响 应 ".getBytes("utf-8"), null));
        Message message = new Message(" 这是等待确认的消息 ".getBytes("utf-8"), props);
        rabbitTemplate.convertAndSend("ex.biz", "biz", message, cd);
    }

}
