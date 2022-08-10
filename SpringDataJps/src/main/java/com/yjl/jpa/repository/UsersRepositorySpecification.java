package com.yjl.jpa.repository;

import com.yjl.jpa.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author yujiale
 * @Date 2022/8/10 21:08
 * @Description JpaSpecificationExecutor
 **/
public interface UsersRepositorySpecification extends JpaRepository<Users, Integer>, JpaSpecificationExecutor<Users> {

}