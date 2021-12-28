package com.yjl.rabbitmq.confirm;


import com.yjl.rabbitmq.utils.RabbitConstant;
import com.yjl.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author yujiale
 * 消费者消息确认
 */
public class Sina {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SINA, false, false, false, null);

        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_TOPIC, "us.#");

        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_SINA, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("腾讯天气收到气象信息：" + new String(body));
                //false只确认签收当前的消息，设置为true的时候则代表签收该消费者所有未签收的消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
