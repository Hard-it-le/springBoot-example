package com.yjl.rabbit.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yjl.rabbit.utils.RabbitUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 * <p>
 * 生产者ttl机制
 */
public class Producer {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        try {
            Channel channel = connection.createChannel();
            HashMap<String, Object> arguments = new HashMap<>();
            //消息队列中消息过期时间，30s
            arguments.put("x-message-ttl", 30 * 1000);
            //消息队列没有消费者，则10s消息过期,消息队列也删除
            arguments.put("x-expires", 10 * 1000);
            channel.queueDeclare("queue.ttl.waiting", true, false, false, arguments);

            channel.exchangeDeclare("ex.ttl.waiting", "direct", true, false, null);
            channel.queueBind("queue.ttl.waiting", "ex.ttl.waiting", "key.ttl.waiting");

            String message = "等待的订单号";

            final AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().contentEncoding("utf-8").deliveryMode(2).build();

            channel.basicPublish("ex.ttl.waiting", "key.ttl.waiting", properties, message.getBytes("UTF-8"));

            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        connection.close();

    }
}
