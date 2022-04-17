package com.yjl.mybatis.service.impl;

import com.yjl.mybatis.entity.User;
import com.yjl.mybatis.mapper.UserMapper;
import com.yjl.mybatis.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    public UserMapper userMapper;


    @Override
    public void selectUserByUsername(String username) {
        User user = userMapper.selectUserByUsername(username);
        System.out.println(user);
    }
}
