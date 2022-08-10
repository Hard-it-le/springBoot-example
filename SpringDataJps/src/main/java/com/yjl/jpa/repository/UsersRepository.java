package com.yjl.jpa.repository;

import com.yjl.jpa.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 * <p>
 * * 参数一 T :当前需要映射的实体
 * * 参数二 ID :当前映射的实体中的OID的类型
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {

}
