package com.yjl.rocket.simple.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/31
 *
 *
 *
 * <p>
 * 同步发送消息
 * <p>
 * 同步传输广泛应用于大量场景，如重要通知消息、短信通知、短信营销系统等。
 * <p>
 * 发送消息要经过五个步骤
 * 1、设置producer的groupName
 * 2、设置instanceName，当一个Jvm需要启动多个Producer的时候，通过设置不同的InstanceName来区分，不设置的话系统使用默认名称“DEFAULT”
 * 3、设置发送失败重试次数，当网络异常时候，如果想保证不丢消息，重试次数尽量设置多
 * 4、设置nameserver地址
 * 5、消息的组装和发送
 * <p>
 * <p>
 * 发送一条消息经过三步
 * 1、客户端发送请求到服务器
 * 2、服务器处理请求
 * 3、服务器想客户端返回应答
 * <p>
 * 如何提升写入性能
 * 1、采用oneWay方式发送
 * 2、增加producer的并发量（使用多个producer同时发送）
 */
public class SyncProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 该producer是线程安全的，可以多线程使用。
        // 建议使用多个Producer实例发送
        // 实例化生产者实例，同时设置生产组名称
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("");
        // 设置实例名称。一个JVM中如果有多个生产者，可以通过实例名称区分
        // 默认DEFAULT
        defaultMQProducer.setInstanceName("");
        // 设置同步发送重试的次数
        defaultMQProducer.setRetryTimesWhenSendFailed(2);
        // 设置异步发送的重试次数
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(2);
        // 设置nameserver的地址
        defaultMQProducer.setNamesrvAddr("");
        // 对生产者进行初始化
        defaultMQProducer.start();
        /**
         * 组装消息
         * topic：主题名称
         * 消息体
         */
        Message message = new Message("tp_demo_04", "hello rocket ".getBytes());


        /**
         *    同步发送消息，如果消息发送失败，则按照setRetryTimesWhenSendFailed设置的次数进行重试
         *     broker中可能会有重复的消息，由应用的开发者进行处理
         */

        final SendResult result = defaultMQProducer.send(message);

        defaultMQProducer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 发送成功的处理逻辑
            }

            /**
             * 重试不算发送失败，只有重试次数耗尽，发生的异常才算发送失败
             * @param e
             */
            @Override
            public void onException(Throwable e) {
                // 发送失败的处理逻辑

            }
        });

        /**
         *   将消息放到Socket缓冲区，就返回，没有返回值，不会等待broker的响应
         *    速度快，会丢消息
         *    单向发送
         */
        defaultMQProducer.sendOneway(message);

        final SendStatus sendStatus = result.getSendStatus();

        System.out.println(sendStatus);

    }
}
