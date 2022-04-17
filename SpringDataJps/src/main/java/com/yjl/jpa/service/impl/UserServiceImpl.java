package com.yjl.jpa.service.impl;

import com.yjl.jpa.entity.User;
import com.yjl.jpa.repository.UserRepository;
import com.yjl.jpa.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    public UserRepository userRepository;

    @Override
    public void save(User user) {
        User save = userRepository.save(user);
        System.out.println(save);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByusername(username);
    }
}
