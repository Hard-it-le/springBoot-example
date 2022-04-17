package com.yjl.mybatis.service;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */

public interface UserService {
    /**
     * 通过用户名称查找用户信息
     * @param username
     */
    void selectUserByUsername(String username);
}
