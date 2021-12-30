package com.yjl.rabbit.test.advanced;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerAckTest {


    @Resource
    private RabbitTemplate rabbitTemplate;

    private
    Random random = new Random();


    @Bean
    public ApplicationRunner runner() {
        return args -> {
            Thread.sleep(1000);
            for (int i = 0; i < 10; i++) {
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setDeliveryTag(i);

                Message message = new Message((" 消息： " + i).getBytes("utf-8"), messageProperties);

                this.rabbitTemplate.convertAndSend("ack.ex", "ack.*", message);
            }
        };
    }

    @Test
    public void sendMessage() {
        String message = rabbitTemplate.execute(new ChannelCallback<String>() {
            @Override
            public String doInRabbit(Channel channel) throws Exception {
                final GetResponse getResponse = channel.basicGet("ack.queue", false);
                if (getResponse == null) {
                    return "你已消费完所有的消息";
                }
                String message = new String(getResponse.getBody());

                if (random.nextInt(10) % 3 == 0) {
                    channel.basicAck(getResponse.getEnvelope().getDeliveryTag(), false);

                    return "已确认的消息:" + message;
                } else {
                    //拒收一条消息
                    channel.basicReject(getResponse.getEnvelope().getDeliveryTag(), true);
//可以拒收多条消息
                   // channel.basicNack(getResponse.getEnvelope().getDeliveryTag(), false, true);
                    return " 拒绝的消息： " + message;
                }

            }
        });
        System.out.println(message);
    }
}
