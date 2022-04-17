package com.yjl.jpa.repository;

import com.yjl.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, Serializable {


    /**
     * 根据用户名查找User
     *
     * @param username
     * @return
     */
    User findByusername(String username);
}

