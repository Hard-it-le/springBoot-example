package com.yjl.rabbit.advanced.consumerAck;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/29
 * <p>
 * 消息可靠性之消费端确认机制
 */
@Component
public class ConsumerListener {


    @RabbitListener(queues = "ack.queue", ackMode = "AUTO")
    public void handleMessageTopic(Channel channel,
                                   @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                                   @Payload byte[] message) {
        System.out.println("rabbit监听的消息，消息内容是：" + new String(message));
        try {
            //手动ack，deliveryTag 表示消息的唯一标志，multiple 表示是否是批量确认
            channel.basicAck(deliveryTag, false);
            //手动nack 告诉broker 消费者处理失败，最后一个参数表示是否需要将消息重新入列.
            channel.basicNack(deliveryTag,
                    false,
                    true);
            //手动拒绝消息。第二个参数表示是否重新入列
            channel.basicReject(deliveryTag,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
