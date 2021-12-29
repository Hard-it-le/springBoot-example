package com.yjl.rabbit.PublisherConfirms;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.yjl.rabbit.utils.RabbitConstant;
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
public class PublisherConfirmsProducers03 {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //创建长连接
        Connection connection = RabbitUtils.getConnection();

        //创建信道
        Channel channel = connection.createChannel();
        //向rabbitmq服务器发送amqp命令，将当前channel标记为发送方确认通道
        channel.confirmSelect();

        channel.queueDeclare(RabbitConstant.QUEUE_NAME_PC, true, false, false, null);
        channel.exchangeDeclare(RabbitConstant.EXCHANGE_NAME_PC, "direct", true, false, null);
        channel.queueBind(RabbitConstant.QUEUE_NAME_PC, RabbitConstant.EXCHANGE_NAME_PC, RabbitConstant.ROUTING_KEY_NAME_PC);

        ConfirmCallback clearOutstandingConfirms1 = new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("编号小于等于 " + deliveryTag + " 的消息都已经被确认了");
                } else {
                    System.out.println("编号为：" + deliveryTag + " 的消息被确认");
                }
            }
        };

        ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

        ConfirmCallback clearOutstandingConfirms = (deliveryTag, multiple) -> {
            if (multiple) {
                System.out.println("编号小于等于 " + deliveryTag + " 的消息都已经被确认了");
                final ConcurrentNavigableMap<Long, String> headMap
                        = outstandingConfirms.headMap(deliveryTag, true);
                // 清空outstandingConfirms中已经被确认的消息信息
                headMap.clear();

            } else {
                // 移除已经被确认的消息
                outstandingConfirms.remove(deliveryTag);
                System.out.println("编号为：" + deliveryTag + " 的消息被确认");
            }
        };

        // 设置channel的监听器，处理确认的消息和不确认的消息
        channel.addConfirmListener(clearOutstandingConfirms, (deliveryTag, multiple) -> {
            if (multiple) {
                // 将没有确认的消息记录到一个集合中
                System.out.println("消息编号小于等于：" + deliveryTag + " 的消息 不确认");
                ConcurrentNavigableMap<Long, String> headMap = outstandingConfirms.headMap(deliveryTag,
                        true);
            } else {
                System.out.println("编号为：" + deliveryTag + " 的消息不确认");
                outstandingConfirms.remove(deliveryTag);
            }
        });

        String message = "hello-";
        for (int i = 0; i < 1000; i++) {
            // 获取下一条即将发送的消息的消息ID
            final long nextPublishSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("ex.pc", "key.pc", null, (message + i).getBytes());
            System.out.println("编号为：" + nextPublishSeqNo + " 的消息已经发送成功，尚未确认");
            outstandingConfirms.put(nextPublishSeqNo, (message + i));
        }

        // 等待消息被确认
        Thread.sleep(10000);

        channel.close();
        connection.close();

    }
}
