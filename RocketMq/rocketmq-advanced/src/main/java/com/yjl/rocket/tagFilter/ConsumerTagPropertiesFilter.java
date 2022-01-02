package com.yjl.rocket.tagFilter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
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
public class ConsumerTagPropertiesFilter {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_grp_06_02");

        consumer.setNamesrvAddr("node1:9876");

//        consumer.subscribe("tp_demo_06_02", MessageSelector.bySql("mykey in ('v0', 'v1')"));
//        consumer.subscribe("tp_demo_06_02", MessageSelector.bySql("mykey = 'v0'"));
        consumer.subscribe("tp_demo_06_02", MessageSelector.bySql("key IS NOT NULL"));


        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                final MessageQueue messageQueue = context.getMessageQueue();
                final String brokerName = messageQueue.getBrokerName();
                final String topic = messageQueue.getTopic();
                final int queueId = messageQueue.getQueueId();

                System.out.println(brokerName + "\t" + topic + "\t" + queueId);

                for (MessageExt msg : msgs) {
                    System.out.println(msg);
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 初始化并启动消费者
        consumer.start();

    }
}