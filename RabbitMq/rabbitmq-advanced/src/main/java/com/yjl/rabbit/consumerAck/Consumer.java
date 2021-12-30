package com.yjl.rabbit.consumerAck;

import com.rabbitmq.client.*;
import com.yjl.rabbit.utils.RabbitUtils;

import java.io.IOException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/29
 *
 * 消息可靠性之消费端确认机制
 */
public class Consumer {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("queue.ca", false, false, false, null);
        //ack设置为false，手动确认消息
        channel.basicConsume(
                "queue.ca",
                false,
                "myConsumer",
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {

                        System.out.println(new String(body));
                        //确认消息
                        channel.basicAck(envelope.getDeliveryTag(), false);
                        /**
                         * 拒绝消息，主要用于拒收多条消息
                         *
                         * 1:消息的标签
                         * 2：拒绝消息确认
                         * 3：将拒绝的消息重新发送到队列中（true表示重新入列，false表示不重发）
                         */
                        channel.basicNack(envelope.getDeliveryTag(), false, true);
                        //拒收一条消息
                        channel.basicReject(envelope.getDeliveryTag(), true);
                    }
                });
    }
}
