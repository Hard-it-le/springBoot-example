package com.kafka.pro;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/05/05
 */
public class Producer01 {
    public static void main(String[] args) throws Exception {

        Map<String, Object> configs = new HashMap<>();
        // 指定初始连接用到的broker地址
        configs.put("bootstrap.servers", "124.222.245.253:9092");
        // 指定key的序列化类
        configs.put("key.serializer", IntegerSerializer.class);
        // 指定value的序列化类
        configs.put("value.serializer", StringSerializer.class);

        // 消息确认
        configs.put("acks", "1");
        // 消息发送失败重试次数
        configs.put("reties", "3");

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(configs);
        /**
         * 用于封装 Producer 的消息
         * topic ： 主题名称
         * paratiton ：分区编号
         * key： 数字作为key
         * value ： 发送的值
         */
        ProducerRecord<Integer, String> record = new ProducerRecord<>(
                "kafka_topic_api_1",
                0,
                0,
                "hello java test 1"
        );

        // 消息的同步确认
        final Future<RecordMetadata> future = producer.send(record);
        final RecordMetadata metadata = future.get(3000, TimeUnit.MILLISECONDS);
        System.out.println("消息的主题：" + metadata.topic());
        System.out.println("消息的分区号：" + metadata.partition());
        System.out.println("消息的偏移量：" + metadata.offset());

        // 关闭生产者
        producer.close();
    }
}
