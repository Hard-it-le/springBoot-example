package com.yjl.rabbit.consumerQos;

import com.rabbitmq.client.*;
import com.yjl.rabbit.utils.RabbitUtils;

import java.io.IOException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 * <p>
 * 消费端限流
 */
public class Consumer {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("queue.qos", false, false, false, null);
        //使用basic做限流，仅对消息推送模式生效
        //表示qos是10个消息，最多有10个消息等待
        channel.basicQos(10);
        //表示最多10个消息等待确认，如果global设置为true，则表示只要使用当前channel的Consumer都生效，false经表示当前Consumer
        channel.basicQos(10, false);
        //prefetchSize表示未确认消息的大小，rabbit没有实现不用管
        channel.basicQos(1000, 10, true);

        channel.basicConsume("queue.qos", false, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {

                //消息确认（批量处理），减少网络流量负载
                channel.basicAck(envelope.getDeliveryTag(), true);

            }
        });
    }
}
