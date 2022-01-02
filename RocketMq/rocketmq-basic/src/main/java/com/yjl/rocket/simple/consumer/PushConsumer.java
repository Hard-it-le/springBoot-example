package com.yjl.rocket.simple.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/31
 * <p>
 * 消费者推送消息
 * <p>
 * 推荐使用
 */
public class PushConsumer {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("");

        pushConsumer.setNamesrvAddr("");
        //订阅主题
        pushConsumer.subscribe("TopicTest", "*");

        //添加消息监听器，一旦有消息推送过来，就进行消费
        pushConsumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                final MessageQueue messageQueue = consumeConcurrentlyContext.getMessageQueue();
                System.out.println(messageQueue);

                for (MessageExt msg : list) {
                    try {
                        System.out.println(new String(msg.getBody(), "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                // 消息消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                // 消息消费失败
//                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        // 初始化消费者，之后开始消费消息
        pushConsumer.start();


    }
}
