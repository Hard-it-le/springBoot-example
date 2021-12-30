package com.yjl.rabbit.dlx;

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
 */
public class Producer {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        try {
            Channel channel = connection.createChannel();

            //声明死信交换机
            channel.exchangeDeclare("ex.dlx", "direct", true);
            //声明死信队列
            channel.queueDeclare("queue.dlx", true, false, false, null);
            //绑定死信交换器和死信队列
            channel.queueBind("queue.dlx", "ex.dlx", "key.dlx");


            HashMap<String, Object> arguments = new HashMap<>();
            //消息队列中消息过期时间，30s
            arguments.put("x-message-ttl", 30 * 1000);
            arguments.put("x-expires", 60 * 60 * 1000);
            //设置该队列所关联的死信交换器（当队列消息TTL 到期后依然没有消费，则加入死信队列）
            arguments.put("x-dead-letter-exchange", "ex.dlx");
            //设置该队列所关联的死信交换器的routingKey，如果没有特殊指定，使用原队列的 routingKey
            arguments.put("x-dead-letter-routing-key", "key.dlx");
            //正常业务的交换器
            channel.exchangeDeclare("ex.biz", "direct", true);
            //声明正常的队列
            channel.queueDeclare("queue.biz", true, false, false, arguments);
            //绑定业务交换机和正常队列
            channel.queueBind("queue.biz", "ex.biz", "key.biz");


            String message = "orderId.111";
            channel.basicPublish("ex.biz", "key.biz", null, message.getBytes());

            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        connection.close();

    }
}
