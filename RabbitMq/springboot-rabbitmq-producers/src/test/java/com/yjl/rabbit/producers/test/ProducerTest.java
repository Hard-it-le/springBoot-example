package com.yjl.rabbit.producers.test;

import com.yjl.rabbit.producers.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    public void sendTest() {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, "boot.#", "boot.mq");
    }
}
