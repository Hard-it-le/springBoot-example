package com.yjl.jpa;

import com.yjl.jpa.entity.User;
import com.yjl.jpa.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class JpaApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private UserService userService;

    @Test
    public void testJps() {
        List<User> userList = userService.findAll();
        userList.stream().forEach(System.out::println);
    }


    @Test
    public void testJpaSave() {
        User user = new User();

        user.setUsername("test02");
        user.setPassword("123456");
        user.setEnabled(0);
        userService.save(user);
    }



}
