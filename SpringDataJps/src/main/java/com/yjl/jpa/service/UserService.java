package com.yjl.jpa.service;

import com.yjl.jpa.entity.User;

import java.util.List;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
public interface UserService {
    /**
     * 保存
     *
     * @param user
     */
    public void save(User user);

    /**
     * 删除
     *
     * @param user
     */
    public void delete(User user);

    /**
     * 查找
     *
     * @return
     */
    public List<User> findAll();


    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUserName(String username);
}
