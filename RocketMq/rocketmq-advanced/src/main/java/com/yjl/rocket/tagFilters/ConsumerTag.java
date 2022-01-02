package com.yjl.rocket.tagFilters;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/31
 */
public class ConsumerTag {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();

        defaultMQPushConsumer.setNamesrvAddr("");

        // defaultMQPushConsumer.subscribe("topic", "*");
        defaultMQPushConsumer.subscribe("topic", "tag-1||tag-0");

        defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                final MessageQueue messageQueue = consumeConcurrentlyContext.getMessageQueue();
                final String brokerName = messageQueue.getBrokerName();
                final String topic = messageQueue.getTopic();
                int queueId = messageQueue.getQueueId();
                System.out.println(brokerName + "\t" + topic + "\t" + queueId);
                for (MessageExt messageExt : list) {
                    System.out.println(messageExt);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });


        defaultMQPushConsumer.start();
    }
}
