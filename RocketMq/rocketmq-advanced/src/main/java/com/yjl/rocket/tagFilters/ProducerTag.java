package com.yjl.rocket.tagFilters;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/31
 */
public class ProducerTag {

    public static void main(String[] args) throws RemotingException, MQClientException, InterruptedException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("");

        defaultMQProducer.setNamesrvAddr("");

        defaultMQProducer.start();


        Message message = null;
        for (int i = 0; i < 10; i++) {

            message = new Message("topic", "tag-" + (i % 3), ("hello rocket - " + i).getBytes());

            defaultMQProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult.getSendStatus());
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable);
                }
            });
        }

        Thread.sleep(3000);
        defaultMQProducer.shutdown();


    }
}
