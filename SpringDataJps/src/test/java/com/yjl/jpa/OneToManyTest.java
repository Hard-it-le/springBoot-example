package com.yjl.jpa;

import com.yjl.jpa.entity.Roles;
import com.yjl.jpa.entity.Users;
import com.yjl.jpa.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * @Author yujiale
 * @Date 2022/8/10 21:40
 * @Description 一对多关联测试
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JpaApplication.class)
public class OneToManyTest {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * 一对多关联关系的添加
     */
    @Test
    public void testSave() {
        //创建一个用户
        Users users = new Users();
        users.setAddress("天津");
        users.setAge(32);
        users.setName("小刚");

        //创建一个角色
        Roles roles = new Roles();
        roles.setRolename("管理员");

        //关联
        roles.getUsers().add(users);
        users.setRoles(roles);

        //保存
        this.usersRepository.save(users);
    }

    /**
     * 一对多关联关系的查询
     */
    @Test
    public void testFind() {
        Optional<Users> findOne = this.usersRepository.findById(4);
        Users users = findOne.get();
        System.out.println(users);
        Roles roles = users.getRoles();
        System.out.println(roles.getRolename());
    }
}