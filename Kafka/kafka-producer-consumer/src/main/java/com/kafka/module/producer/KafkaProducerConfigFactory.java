package kafka.module.producer;

/**
 * @program: authing-risk-plat
 * @author: yjl
 * @created: 2022/05/11
 * <p>
 * 构建 kafka 生产者配置的工厂类
 */
public class KafkaProducerConfigFactory {

    public static kafka.module.producer.KafkaProducerConfig build(String bootstrapServers) {
        return build(bootstrapServers, "0");
    }


    public static kafka.module.producer.KafkaProducerConfig build(String bootstrapServers, String acks) {
        return build(bootstrapServers, acks, "");
    }


    public static kafka.module.producer.KafkaProducerConfig build(String bootstrapServers, String acks, Integer retries) {
        kafka.module.producer.KafkaProducerConfig config = new kafka.module.producer.KafkaProducerConfig();
        config.setBootstrapServers(bootstrapServers);
        config.setAcks(acks);
        config.setRetries(retries);
        return config;
    }


    public static kafka.module.producer.KafkaProducerConfig build(String bootstrapServers, Integer retries) {
        kafka.module.producer.KafkaProducerConfig config = new kafka.module.producer.KafkaProducerConfig();
        config.setBootstrapServers(bootstrapServers);
        config.setRetries(retries);
        return config;
    }


    public static kafka.module.producer.KafkaProducerConfig build(String bootstrapServers, String acks, Integer retries, String clientId) {
        kafka.module.producer.KafkaProducerConfig config = new kafka.module.producer.KafkaProducerConfig();
        config.setBootstrapServers(bootstrapServers);
        config.setAcks(acks);
        config.setRetries(retries);
        config.setClientId(clientId);
        return config;
    }


    public static kafka.module.producer.KafkaProducerConfig build(String bootstrapServers, String acks, String clientId) {
        kafka.module.producer.KafkaProducerConfig config = new kafka.module.producer.KafkaProducerConfig();
        config.setBootstrapServers(bootstrapServers);
        config.setAcks(acks);
        config.setClientId(clientId);
        return config;
    }


    public static kafka.module.producer.KafkaProducerConfig build(String bootstrapServers, Integer retries, String clientId) {
        kafka.module.producer.KafkaProducerConfig config = new kafka.module.producer.KafkaProducerConfig();
        config.setBootstrapServers(bootstrapServers);
        config.setRetries(retries);
        config.setClientId(clientId);
        return config;
    }

    public static kafka.module.producer.KafkaProducerConfig build(kafka.module.producer.KafkaProducerConfig kafkaProducerConfig) {
        kafka.module.producer.KafkaProducerConfig config = new kafka.module.producer.KafkaProducerConfig();
        config = kafkaProducerConfig;
        return config;
    }
}
