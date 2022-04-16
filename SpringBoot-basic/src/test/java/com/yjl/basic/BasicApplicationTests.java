package com.yjl.basic;

import com.yjl.basic.controller.TestController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 单元测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class BasicApplicationTests {


    @Resource
    public TestController testController;


    @Test
    void contextLoads() {
        System.out.println("单元测试");
    }

    @Test
    public void testDemo() {
        String s = testController.testDemo();
        System.out.println(s);
    }

}
