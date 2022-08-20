package kafka.module.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @program: authing-risk-plat
 * @author: yjl
 * @created: 2022/05/11
 * <p>
 */
public class KafkaProducerClient<K, V> implements kafka.module.producer.AsyncMessageProducer<K, V> {


    /**
     * Kafka 消息生产者使用的配置信息
     */
    private kafka.module.producer.KafkaProducerConfig producerConfig;

    /**
     * Kafka 消息生产者
     */
    private Producer<K, V> producer;

    private String topic;


    public KafkaProducerClient(kafka.module.producer.KafkaProducerConfig kafkaProducerConfig) throws NullPointerException {

        if (kafkaProducerConfig == null) {

            throw new NullPointerException("Create KafkaProducer failed: `kafkaProducerConfig could not be null`.");
        }
        this.producerConfig = kafkaProducerConfig;
        this.producer = new KafkaProducer<>(this.producerConfig.toConfigMap());

    }


    /**
     * 构造一个 Kafka 异步消息生产者。
     * * @param kafkaProducerConfig Kafka 生产者配置信息，不允许为 {@code null}
     *
     * @throws NullPointerException 如果 Kafka 生产者配置信息为 {@code null}，将会抛出此异常
     */
    public KafkaProducerClient(kafka.module.producer.KafkaProducerConfig kafkaProducerConfig, String topic) throws NullPointerException {
        this.topic = topic;
        if (kafkaProducerConfig == null) {

            throw new NullPointerException("Create KafkaProducer failed: `kafkaProducerConfig could not be null`.");
        }
        this.producerConfig = kafkaProducerConfig;
        this.producer = new KafkaProducer<>(this.producerConfig.toConfigMap());

    }


    @Override
    public void sendMessage(String topic, V value) {
        this.sendMessage(topic, (Integer) null, (K) null, value, (Long) null, (Callback) null);
    }


    @Override
    public void sendMessage(String topic, Integer partition, K key, V value) {
        this.sendMessage(topic, partition, key, value, (Long) null, (Callback) null);
    }

    @Override
    public void sendMessage(String topic, Integer partition, K key, V value, Long timestamp) {
        this.sendMessage(topic, partition, key, value, timestamp, (Callback) null);
    }

    @Override
    public void sendMessage(String topic, Integer partition, K key, V value, Callback callback) {
        this.sendMessage(topic, partition, key, value, (Long) null, callback);
    }

    @Override
    public void sendMessage(String topic, Integer partition, K key, V value, Long timestamp, Callback callback) {
        ProducerRecord<K, V> kvProducerRecord;
        if (null == timestamp) {
            kvProducerRecord = new ProducerRecord<>(topic, partition, key, value);
        } else {
            kvProducerRecord = new ProducerRecord<>(topic, partition, timestamp, key, value);
        }
        this.sendMessage(kvProducerRecord, callback);
    }

    @Override
    public void sendMessage(ProducerRecord<K, V> record) {
        this.sendMessage(record, null);
    }

    @Override
    public void sendMessage(ProducerRecord<K, V> record, Callback callback) {
        try {
            Future<RecordMetadata> send = this.producer.send(record, callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 关闭 kafka 生产者
     */
    @Override
    public void close() {
        this.producer.flush();
        this.producer.close();
    }

    @Override
    public void flushData() {
        this.producer.flush();
    }

    @Override
    public void sendMessageList(String topic, List<String> messageList) {
        Iterator<String> iterator = messageList.iterator();
        while (iterator.hasNext()) {
            V message = (V) iterator.next();
            this.sendMessage(topic, message);
        }
    }

    @Override
    public void transactionSendMessageList(String topic, List<String> messageList) {
        this.producer.initTransactions();
        try {
            for (int i = 0; i < messageList.size(); ++i) {
                this.sendMessage(topic, (V) messageList.get(i));
            }
            this.producer.commitTransaction();
        } catch (ProducerFencedException var4) {
            this.flushData();
        } catch (KafkaException var5) {
            this.producer.abortTransaction();
        }
        this.flushData();
    }


}
