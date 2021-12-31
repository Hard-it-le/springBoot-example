package com.yjl.rocket.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/31
 * <p>
 * 同步发送消息
 *
 * 同步传输广泛应用于大量场景，如重要通知消息、短信通知、短信营销系统等。
 */
public class SyncProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        // 在实例化生产者的同时，指定了生产组名称
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("");
        //设置nameServerip质地
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");

        // 对生产者进行初始化
        defaultMQProducer.start();

        for (int i = 0; i < 100; i++) {
            /**
             * 创建消息，
             * topic是主题名称，
             * tags是消息的标签
             * 第三个参数是消息内容
             */
            Message message = new Message(
                    "topic.rocket",
                    "TagA",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            // 发送消息
            final SendResult sendResult = defaultMQProducer.send(message);
            System.out.printf("%s%n", sendResult);
        }
        //生产者发送消息后去要关闭消费者
        defaultMQProducer.shutdown();
    }
}
