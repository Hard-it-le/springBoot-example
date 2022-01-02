package com.yjl.rocket.orderMessage;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/01/02
 */
public class OrderMessageProducer {

    public static void main(String[] args) {


        try {
            DefaultMQProducer defaultMQProducer = new DefaultMQProducer("");
            defaultMQProducer.start();
            for (int i = 0; i < 10; i++) {
                int orderId = i;
                for (int j = 0; j < 5; j++) {
                    Message msg = new Message("OrderTopicTest", "order_" + orderId, "KEY" + orderId,
                            ("order_" + orderId + " step " + j).getBytes(RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = defaultMQProducer.send(msg, new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                            Integer id = (Integer) arg;
                            int index = id % mqs.size();
                            return mqs.get(index);
                        }
                    }, orderId);

                    System.out.printf("%s%n", sendResult);
                }
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }


    }
}
