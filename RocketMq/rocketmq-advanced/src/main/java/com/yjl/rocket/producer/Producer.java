package com.yjl.rocket.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/31
 * <p>
 * 消息发送机制
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 该producer是线程安全的，可以多线程使用。
        // 建议使用多个Producer实例发送
        // 实例化生产者实例，同时设置生产组名称
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("");
        // 设置实例名称。一个JVM中如果有多个生产者，可以通过实例名称区分
        // 默认DEFAULT
        defaultMQProducer.setInstanceName("");
        // 设置同步发送重试的次数
        defaultMQProducer.setRetryTimesWhenSendFailed(2);
        // 设置异步发送的重试次数
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(2);

        // 设置nameserver的地址
        defaultMQProducer.setNamesrvAddr("");
        // 对生产者进行初始化
        defaultMQProducer.start();

        // 组装消息
        Message message = new Message("tp_demo_04", "hello rocket ".getBytes());


        // 同步发送消息，如果消息发送失败，则按照setRetryTimesWhenSendFailed设置的次数进行重试
        // broker中可能会有重复的消息，由应用的开发者进行处理
        final SendResult result = defaultMQProducer.send(message);

        defaultMQProducer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 发送成功的处理逻辑
            }

            @Override
            public void onException(Throwable e) {
                // 发送失败的处理逻辑
                // 重试次数耗尽，发生的异常
            }
        });

        // 将消息放到Socket缓冲区，就返回，没有返回值，不会等待broker的响应
        // 速度快，会丢消息
        // 单向发送
        defaultMQProducer.sendOneway(message);

        final SendStatus sendStatus = result.getSendStatus();

    }
}
