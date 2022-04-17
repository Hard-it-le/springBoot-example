package com.yjl.mybatis.controller;

import com.yjl.mybatis.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    public UserService userService;

    @RequestMapping("/selectUserByUsername")
    public void selectUserByUsername(String username) {
        userService.selectUserByUsername(username);
    }
}
