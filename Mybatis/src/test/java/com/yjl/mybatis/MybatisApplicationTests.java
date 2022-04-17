package com.yjl.mybatis;

import com.yjl.mybatis.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
class MybatisApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    public UserController userController;


    @Test
    public void mybatisTestAnnotation() {
        userController.selectUserByUsername("nacos");
    }


    @Test
    public void mybatisTestXml() {
        userController.selectUserByOne();
    }
}
