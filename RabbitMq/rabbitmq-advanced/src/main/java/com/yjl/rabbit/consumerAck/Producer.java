package com.yjl.rabbit.consumerAck;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yjl.rabbit.utils.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/29
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("ex.ca", "direct", false, false, null);
        channel.queueDeclare("queue.ca", false, false, false, null);

        channel.queueBind("queue.ca", "ex.ca", "key.ca");
        String message = "hello-";
        for (int i = 0; i < 5; i++) {
            channel.basicPublish("ex.ca", "key.ca", null, (message + 1).getBytes());
        }

        channel.close();
        connection.close();

    }

}
