package com.yjl.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @program: kafka
 * @author: yjl
 * @created: 2022/05/06
 */
@Component
public class Consumer {
    @KafkaListener(topics = "topic-springboot_01")
    public void onMessage(ConsumerRecord<Integer, String> record) {
        System.out.println("消费者收到的消息："
                + record.topic() + "\t"
                + record.partition() + "\t"
                + record.offset() + "\t"
                + record.key() + "\t"
                + record.value());
    }

}
