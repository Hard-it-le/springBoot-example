package com.yjl.rabbit.messageReliability;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yjl.rabbit.utils.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/29
 * <p>
 * 按批次进行发送消息同步等待确认
 */
public class Producers02 {
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

        //发送消息
        String message = "hello-";

        //最小发送批次数
        int batchSize = 10;
        //用于记录等待确认消息
        int outstandingConfirms = 0;
        for (int i = 0; i < 103; i++) {
            //发送消息
            channel.basicPublish("ex.pc", "key.pc", null, (message + i).getBytes());
            outstandingConfirms++;
            if (outstandingConfirms == batchSize) {
                channel.waitForConfirmsOrDie(5_000);
                System.out.println(" 批消息确认 ");
                outstandingConfirms = 0;
            }

        }

        if (outstandingConfirms > 0) {
            channel.waitForConfirmsOrDie(5_000);
            System.out.println(" 剩余消息已经被确认 ");
        }

        channel.close();
        connection.close();

    }
}
