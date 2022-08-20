package com.kafka.pro;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/05/06
 */
public class Producer02 {
    public static void main(String[] args) throws Exception {

        Map<String, Object> configs = new HashMap<>();
        // 指定初始连接用到的broker地址
        configs.put("bootstrap.servers", "124.222.245.253:9092");
        // 指定key的序列化类
        configs.put("key.serializer", IntegerSerializer.class);
        // 指定value的序列化类
        configs.put("value.serializer", StringSerializer.class);

        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(configs);

        // 用于设置用户自定义的消息头字段
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("biz.name", "producer.demo".getBytes()));

        for (int i = 0; i < 100; i++) {
            ProducerRecord<Integer, String> record = new ProducerRecord<Integer, String>(
                    "topic_1",
                    0,
                    i,
                    "hello lagou " + i,
                    headers
            );

            // 消息的异步确认
            Future<RecordMetadata> send = producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("消息的主题：" + metadata.topic());
                        System.out.println("消息的分区号：" + metadata.partition());
                        System.out.println("消息的偏移量：" + metadata.offset());
                    } else {
                        System.out.println("异常消息：" + exception.getMessage());
                    }
                }
            });

            send.get();
        }

        // 关闭生产者
        producer.close();
    }
}
