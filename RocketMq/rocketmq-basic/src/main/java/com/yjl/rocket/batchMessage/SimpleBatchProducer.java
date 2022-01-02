package com.yjl.rocket.batchMessage;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/01/02
 *
 * 批量发送
 *
 * 如果批量消息大于1MB就不要用一个批次发送，而要拆分成多个批次消息发送。
 * 也就是说，一个批次消息的大小不要超过1 MB
 */
public class SimpleBatchProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("");
        defaultMQProducer.start();


        String topic = "BatchTest";

        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "Tag", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID003", "Hello world 2".getBytes()));

        defaultMQProducer.send(messages);
        defaultMQProducer.shutdown();
    }
}
