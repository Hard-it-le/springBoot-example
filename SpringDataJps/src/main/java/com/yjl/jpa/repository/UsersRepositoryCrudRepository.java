package com.yjl.jpa.repository;

import com.yjl.jpa.entity.Users;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author yujiale
 * @Date 2022/8/10 21:06
 * @Description CrudRepository接口
 **/
public interface UsersRepositoryCrudRepository extends CrudRepository<Users, Integer> {

}
