package com.yjl.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: kafka
 * @author: yjl
 * @created: 2022/05/06
 */
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic1() {
        return new NewTopic("nptc-01", 3, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("nptc-02", 5, (short) 1);
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "124.222.245.253:9092");
        KafkaAdmin admin = new KafkaAdmin(configs);
        return admin;
    }

    @Bean
    @Autowired
    public KafkaTemplate<Integer, String> kafkaTemplate(ProducerFactory<Integer, String> producerFactory) {

        // 覆盖ProducerFactory原有设置
        Map<String, Object> configsOverride = new HashMap<>();
        configsOverride.put(ProducerConfig.BATCH_SIZE_CONFIG, 200);

        KafkaTemplate<Integer, String> template = new KafkaTemplate<Integer, String>(
                producerFactory, configsOverride
        );
        return template;
    }
}
