package com.yjl.jpa.repository;

import com.yjl.jpa.entity.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @Author yujiale
 * @Date 2022/8/10 21:07
 * @Description Repository   @Query
 **/
public interface UsersRepositoryQueryAnnotation extends Repository<Users, Integer> {

    @Query("from Users where name = ?1")
    List<Users> queryByNameUseHQL(String name);

    @Query(value = "select * from t_users where name = ?1", nativeQuery = true)
    List<Users> queryByNameUseSQL(String name);

    @Query("update Users set name  = ?1 where id  = ?2")
    @Modifying
        //需要执行一个更新操作
    void updateUsersNameById(String name, Integer id);
}