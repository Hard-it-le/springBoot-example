package com.yjl.rabbit.messageReliability;

import com.rabbitmq.client.AMQP;
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
 * 消息可靠性之生产者
 *
 * 单独一条进行发送消息同步等待确认
 */
public class Producers01 {


    public static void main(String[] args) throws IOException, TimeoutException {
        //创建长连接
        Connection connection = RabbitUtils.getConnection();

        //创建信道
        Channel channel = connection.createChannel();
        //向rabbitmq服务器发送amqp命令，将当前channel标记为发送方确认通道
        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

        channel.queueDeclare("queue.pc", true, false, false, null);
        channel.exchangeDeclare("ex.pc", "direct", true, false, null);
        channel.queueBind("queue.pc", "ex.pc", "key.pc");

        //发送消息
        String message = "hello";
        channel.basicPublish("ex.pc", "key.pc", null, message.getBytes());
        //同步方式等待MQ确认消息

        try {
            channel.waitForConfirmsOrDie(5_000);
            System.out.println(" 发送消息已确认：message= " + message);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(" 消息被拒绝！ mesmess};");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("发送消息的通道不是PublisherConfirms通道");
        } catch (TimeoutException e) {
            e.printStackTrace();
            System.err.println("等待消息超时，message=" + message);
        }
        channel.close();
        connection.close();

    }
}
