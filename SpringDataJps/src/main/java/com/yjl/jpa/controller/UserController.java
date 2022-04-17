package com.yjl.jpa.controller;

import com.yjl.jpa.entity.User;
import com.yjl.jpa.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
@RestController
@RequestMapping("/jps")
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping("/findAll")
    public List<User> findAllUser() {
        List<User> all = userService.findAll();
        return all;
    }
}
