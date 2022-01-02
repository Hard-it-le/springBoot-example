package com.yjl.rocket.scheduled;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/01/02
 */
public class ScheduledMessageProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("");

        defaultMQProducer.start();

        int totalMessageToSend = 100;

        for (int i = 0; i < totalMessageToSend; i++) {

            Message message = new Message("TopicTest", ("hello scheduled message" + i).getBytes());

            message.setDelayTimeLevel(3);

            defaultMQProducer.send(message);

        }

        defaultMQProducer.shutdown();
    }
}
