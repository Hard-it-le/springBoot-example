package com.yjl.rocket.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/31
 * <p>
 * 异步发送消息
 * <p>
 * 异步传输一般用于响应时间敏感的业务场景。
 */
public class AsyncProducer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("");
        defaultMQProducer.setNamesrvAddr("127.0.0.1");
        defaultMQProducer.start();
        //消息重试次数
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 100;
        //由于是异步发送，这里引入一个countDownLatch，保证所有Producer发送消息的回调方法都执行完了再停止Producer服务。
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        //循环发送消息次数
        for (int i = 0; i < messageCount; i++) {
            try {
                final int index = i;
                /**
                 * topic：主题名称
                 * tags：标签
                 * keys：路由key
                 */
                Message msg = new Message("topic_rocket",
                        "TagA",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 消息的异步发送
                defaultMQProducer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d 发送成功 %s %n", index, sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d 发送失败 %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 由于是异步发送消息，上面循环结束之后，消息可能还没收到broker的响应
        // 如果不sleep一会儿，就报错
        countDownLatch.await(5, TimeUnit.SECONDS);
        defaultMQProducer.shutdown();

    }
}
