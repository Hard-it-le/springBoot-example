package com.yjl.jpa.repository;

import com.yjl.jpa.entity.Users;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @Author yujiale
 * @Date 2022/8/10 21:05
 * @Description Repository接口的方法名称命名查询
 **/
public interface UsersRepositoryByName extends Repository<Users, Integer> {

    /**
     * 方法的名称必须要遵循驼峰式命名规则。findBy(关键字)+属性名称(首字母要大写)+查询条件(首字母大写)
     *
     * @param name
     * @return
     */
    List<Users> findByName(String name);

    List<Users> findByNameAndAge(String name, Integer age);

    List<Users> findByNameLike(String name);
}
