package com.yjl.rocket.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/31
 * <p>
 * 单向发送
 * <p>
 * 单向传输用于需要中等可靠性的情况，例如日志收集
 */
public class OnewayProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("TopicTest",
                    "TagA",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //调用send message将消息传递给其中一个broker
            producer.sendOneway(msg);
        }
        //等待发送完成
        Thread.sleep(5000);
        producer.shutdown();
    }
}
