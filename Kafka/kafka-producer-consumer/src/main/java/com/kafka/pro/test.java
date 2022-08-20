package com.kafka.pro;

import kafka.module.producer.KafkaProducerClient;
import kafka.module.producer.KafkaProducerConfig;
import kafka.module.producer.KafkaProducerConfigFactory;
import org.apache.kafka.clients.producer.ProducerRecord;

public class test {
    public static void main(String[] args) {
        KafkaProducerConfig kafkaProducerConfig = KafkaProducerConfigFactory.build("124.222.245.253:9092");

        KafkaProducerClient kafkaProducerClient = new KafkaProducerClient<>(kafkaProducerConfig);
        System.out.println("12312312");
        kafkaProducerClient.sendMessage("test_send", "1231241241414");


        kafkaProducerClient.sendMessage(new ProducerRecord<String, String>(
                "test_send4",
                0,
                "hello 31231241241 ",
                "24515421515151"
        ), (recordMetadata, exception) -> {
            if (exception == null) {
                System.out.println("消息的主题：" + recordMetadata.topic());
                System.out.println("消息的分区号：" + recordMetadata.partition());
                System.out.println("消息的偏移量：" + recordMetadata.offset());
            } else {
                System.out.println("异常消息：" + exception.getMessage());
            }
        });

        kafkaProducerClient.close();
    }
}
