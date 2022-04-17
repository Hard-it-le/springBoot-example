package com.yjl.mybatis.mapper;

import com.yjl.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名称查找用户
     *
     * @param username
     * @return
     */
    @Select("select * from users where username = #{username}")
    User selectUserByUsername(String username);
}
