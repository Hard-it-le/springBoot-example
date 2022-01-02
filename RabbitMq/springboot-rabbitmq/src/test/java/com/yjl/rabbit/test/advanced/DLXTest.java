package com.yjl.rabbit.test.advanced;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2021/12/30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DLXTest {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    public void distributeGo() {
        rabbitTemplate.convertAndSend("ex.go", "ex.go.*", "送单到石景山\n" +
                "x\n" +
                "小\n" +
                "区，\n" +
                "请在\n" +
                "10\n" +
                "秒内接受任务");
        System.out.println("任务一下发");
    }

    @Test
    public void getAccumulatedTask() {
        Object o = rabbitTemplate.receiveAndConvert("queue.go.dlx");
        System.out.println(0);
        System.out.println("转入到死信队列");
    }
}
