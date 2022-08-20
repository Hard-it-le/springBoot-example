package kafka.module.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: authing-risk-plat
 * @author: yjl
 * @created: 2022/05/11
 * <p>
 * kafka 生产者配置类
 * toConfigMap 初始化 kafka生产者
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KafkaProducerConfig {

    /**
     * Kafka 集群启动地址，例如：host1:port1,host2:port2,...
     */
    private String bootstrapServers = "";


    private String KeySerializer = "org.apache.kafka.common.serialization.StringSerializer";

    private String valueSerializer = "org.apache.kafka.common.serialization.StringSerializer";


    /**
     * Kafka 生产者确认消息被送达的模式，默认为 1，允许的值为：0、1、-1、all。
     */
    private String acks = "0";


    /**
     * 在消息发送至 Kafka 之前，生产者允许使用的最大缓存大小，默认为 33554432 字节（32 MB）
     */
    private Long bufferMemory = 33554432L;

    /**
     * 生产者数据压缩类型，默认为 "none"，允许的值为：none、gzip、snappy、lz4
     */
    private String compressionType = "none";

    /**
     * 消息发送失败是否进行重试，如果该值大于 0，则需要进行重试，默认为 0，不重试
     */
    private Integer retries = 0;

    /**
     * 合并后的消息最大字节数，默认为 16384 字节（16 KB）
     */
    private Integer batchSize = 16384;


    /**
     * 生产者 ID，用于在 Kafka 服务中追踪请求来源，默认为空字符串
     */
    private String clientId = "";

    /**
     * 连接最大空闲时间，默认为 540000 毫秒（8 分钟）
     */
    private Long connectionsMaxIdleMs = 540000L;

    /**
     * 消息发送延迟时间，默认为 0 毫秒（不延迟）
     */
    private Long lingerMs = 1L;


    /**
     * KafkaProducer.send() 或 KafkaProducer.partitionsFor() 方法最大阻塞时间，默认为 60000 毫秒（60 秒）
     */
    private Long maxBlockMs = 60000L;

    /**
     * 每次消息发送请求允许的最大字节数，默认为 1048576 字节（1 MB）
     */
    private Integer maxRequestSize = 1048576;


    /**
     * Socket 读取缓存大小，默认为 32768 字节（32 KB）
     */
    private Integer receiveBufferBytes = 32768;

    /**
     * 请求超时时间，默认为 30000 毫秒（30 秒）
     */
    private Integer requestTimeoutMs = 30000;

    /**
     * Socket 写入缓存大小，默认为 131072 字节（128 KB）
     */
    private Integer sendBufferBytes = 131072;


    /**
     * 当设置为“true”时，生产者将确保流中只写入每条消息的一个副本。如果为“false”，则由于代理失败等原因导致的生产者重试可能会在流中写入重试消息的副本。
     */
    private Boolean enableIdempotent = false;

    /**
     * 指定了生产者在收到服务器响应之前可以发送多少个消息,默认为1
     */
    private Integer maxInFlightRequestsPerSecondConnections = 5;


    /**
     * 指标的最高记录级别。,INFO 和 DEBUG 两个选项
     */
    private String metricsRecordingLevel = "INFO";


    /**
     * 重新连接到重复连接失败的代理时等待的最大时间（毫秒）。如果提供，每台主机的退避将在每次连续连接失败时呈指数级增加，直至达到此最大值。在计算退避增加后，添加20%的随机抖动以避免连接风暴。
     */
    private Long reconnectBackoffMaxMs = 1000L;


    /**
     * 尝试重新连接到给定主机之前等待的基本时间量。这样可以避免在紧密循环中重复连接到主机。此回退适用于客户端到代理的所有连接尝试。
     */
    private Long reconnectBackoffMs = 50L;


    /**
     * 尝试重试对给定主题分区的失败请求之前等待的时间。这避免了在某些故障情况下在紧密循环中重复发送请求。
     */
    private Long retryBackoffMs = 100L;



    /**
     * 根据当前配置信息返回一个用于构造 {@link org.apache.kafka.clients.producer.Producer} 实例的配置信息 {@code Map}。
     *
     * @return Kafka 配置信息 {@code Map}
     */
    public Map<String, Object> toConfigMap() {
        HashMap<String, Object> configMap = new HashMap<>(16);
        configMap.put("bootstrap.servers", bootstrapServers);
        configMap.put("key.serializer", KeySerializer);
        configMap.put("value.serializer", valueSerializer);
        configMap.put("acks", acks);
        configMap.put("buffer.memory", bufferMemory);
        configMap.put("compression.type", compressionType);
        configMap.put("retries", retries);
        configMap.put("batch.size", batchSize);
        configMap.put("client.id", clientId);
        configMap.put("connections.max.idle.ms", connectionsMaxIdleMs);
        configMap.put("linger.ms", lingerMs);
        configMap.put("max.block.ms", maxBlockMs);
        configMap.put("max.request.size", maxRequestSize);
        configMap.put("receive.buffer.bytes", receiveBufferBytes);
        configMap.put("request.timeout.ms", requestTimeoutMs);
        configMap.put("send.buffer.bytes", sendBufferBytes);
        configMap.put("enable.idempotence", enableIdempotent);
        configMap.put("max.in.flight.requests.per.connection", maxInFlightRequestsPerSecondConnections);
        configMap.put("metrics.recording.level", metricsRecordingLevel);
        configMap.put("reconnect.backoff.max.ms", reconnectBackoffMaxMs);
        configMap.put("reconnect.backoff.ms", reconnectBackoffMs);
        configMap.put("retry.backoff.ms", retryBackoffMs);


        return configMap;
    }


}
