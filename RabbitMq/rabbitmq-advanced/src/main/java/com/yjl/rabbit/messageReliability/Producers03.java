package com.yjl.rabbit.messageReliability;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.yjl.rabbit.utils.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/29
 * <p>
 * 按批次进行发送消息同步等待确认
 */
public class Producers03 {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //创建长连接
        Connection connection = RabbitUtils.getConnection();

        //创建信道
        Channel channel = connection.createChannel();
        //向rabbitmq服务器发送amqp命令，将当前channel标记为发送方确认通道
        channel.confirmSelect();

        channel.queueDeclare("queue.pc", true, false, false, null);
        channel.exchangeDeclare("ex.pc", "direct", true, false, null);
        channel.queueBind("queue.pc", "ex.pc", "key.pc");

//        ConfirmCallback clearOutstandingConfirms = new ConfirmCallback() {
//            @Override
//            public void handle(long l, boolean b) throws IOException {
//
//            }
//        };


        ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

        ConfirmCallback clearOutstandingConfirms = (deliveryTag, multiple) -> {
            if (multiple) {
                System.out.println("编号小于等于" + deliveryTag + "的消息已经被确认了");
                final ConcurrentNavigableMap<Long, String> headMap =
                        outstandingConfirms.headMap(deliveryTag, true);
                headMap.clear();
            } else {
                outstandingConfirms.remove(deliveryTag);
                System.out.println("编号为：" + deliveryTag + "的消息已经被确认了");
            }
        };

        channel.addConfirmListener(clearOutstandingConfirms, (deliveryTag, multiple) -> {
            if (multiple) {
                System.out.println("编号小于等于" + deliveryTag + "的消息不确认了");
                final ConcurrentNavigableMap<Long, String> headMap =
                        outstandingConfirms.headMap(deliveryTag, true);
                headMap.clear();
            } else {
                outstandingConfirms.remove(deliveryTag);
                System.out.println("编号为：" + deliveryTag + "的消息不确认了");
            }
        });


        channel.close();
        connection.close();

    }
}
