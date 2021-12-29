package com.yjl.rabbit.persistent;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yjl.rabbit.utils.RabbitConstant;
import com.yjl.rabbit.utils.RabbitUtils;

import java.io.IOException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/29
 * 生产者持久化
 */
public class Producer {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();


        Channel channel = connection.createChannel();


        channel.queueDeclare(RabbitConstant.QUEUE_NAME_PERSISTENT, true, false, false, null);
        //持久化交换机
        channel.exchangeDeclare(RabbitConstant.EXCHANGE_NAME_PERSISTENT, "direct", true, false, null);
        channel.queueBind(RabbitConstant.QUEUE_NAME_PC, RabbitConstant.EXCHANGE_NAME_PERSISTENT, RabbitConstant.ROUTING_KEY_NAME_PERSISTENT);

        /**
         deliveryMode 参数2 是持久化消息
         */
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2).build();


        String message = "hello";
        channel.basicPublish(RabbitConstant.EXCHANGE_NAME_PERSISTENT, RabbitConstant.ROUTING_KEY_NAME_PERSISTENT, properties, message.getBytes());

    }
}
