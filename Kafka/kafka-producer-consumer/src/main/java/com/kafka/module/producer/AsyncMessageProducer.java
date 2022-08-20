package kafka.module.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;

/**
 * @program: authing-risk-plat
 * @author: yjl
 * @created: 2022/05/11
 * <p>
 * kafka 生产者接口层
 */
public interface AsyncMessageProducer<K, V> {


    /**
     * 消息发送
     *
     * @param topic
     * @param value
     */
    void sendMessage(String topic, V value);


    /**
     * 消息发送
     *
     * @param topic
     * @param partition
     * @param key
     * @param value
     */
    void sendMessage(String topic, Integer partition, K key, V value);


    /**
     * 消息发送
     *
     * @param topic
     * @param partition
     * @param key
     * @param value
     * @param timestamp
     */
    void sendMessage(String topic, Integer partition, K key, V value, Long timestamp);


    /**
     * 消息发送
     *
     * @param topic
     * @param partition
     * @param key
     * @param value
     * @param callback
     */
    void sendMessage(String topic, Integer partition, K key, V value, Callback callback);

    /**
     * 消息发送
     *
     * @param topic
     * @param partition
     * @param key
     * @param value
     * @param timestamp
     * @param callback
     */
    void sendMessage(String topic, Integer partition, K key, V value, Long timestamp, Callback callback);


    /**
     * 消息发送
     *
     * @param record
     */
    void sendMessage(ProducerRecord<K, V> record);

    /**
     * 消息发送
     *
     * @param record
     * @param callback
     */
    void sendMessage(ProducerRecord<K, V> record, Callback callback);

    /**
     * 关闭kafka连接
     */
    void close();


    /**
     * 清空 kafka 数据
     */
    void flushData();

    /**
     * 消息批量发送
     *
     * @param topic
     * @param messageList
     */
    void sendMessageList(String topic, List<String> messageList);


    /**
     * 事务消息批量发送
     *
     * @param topic
     * @param messageList
     */
    void transactionSendMessageList(String topic, List<String> messageList);


}
