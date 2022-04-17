package com.yjl.basic;

import com.yjl.basic.config.MyProperties;
import com.yjl.basic.controller.TestController;
import com.yjl.basic.entity.Person;
import com.yjl.basic.entity.Student;
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

    @Resource
    public Person person;


    @Test
    void contextLoads() {
        System.out.println("单元测试");
    }

    @Test
    public void testDemo() {
        String s = testController.testDemo();
        System.out.println(s);
    }

    @Resource
    public Student student;

    @Test
    public void testValuesLoad() {
        System.out.println(student);
    }

    @Resource
    public MyProperties properties;

    @Test
    public void testMyProperties() {
        System.out.println(properties);
    }


}
