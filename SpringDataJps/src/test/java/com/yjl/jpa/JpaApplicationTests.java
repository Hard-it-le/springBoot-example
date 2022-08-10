package com.yjl.jpa;

import com.yjl.jpa.entity.Users;
import com.yjl.jpa.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
class JpaApplicationTests {
    @Resource
    private UsersRepository usersRepository;
    @Resource
    private UsersRepositoryByName usersRepositoryByName;
    @Resource
    private UsersRepositoryQueryAnnotation usersRepositoryQueryAnnotation;
    @Resource
    private UsersRepositoryCrudRepository usersRepositoryCrudRepository;
    @Resource
    private UsersRepositoryPagingAndSorting usersRepositoryPagingAndSorting;
    @Resource
    private UsersRepositorySpecification usersRepositorySpecification;

    @Test
    public void testSave() {
        Users users = new Users();
        users.setAddress("上海市");
        users.setAge(24);
        users.setName("王五");
        this.usersRepository.save(users);
    }

    /**
     * Repository--方法名称命名测试
     */
    @Test
    public void testFindByName() {
        List<Users> list = this.usersRepositoryByName.findByName("张三");
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * Repository--方法名称命名测试
     */
    @Test
    public void testFindByNameAndAge() {
        List<Users> list = this.usersRepositoryByName.findByNameAndAge("张三", 20);
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * Repository--方法名称命名测试
     */
    @Test
    public void testFindByNameLike() {
        List<Users> list = this.usersRepositoryByName.findByNameLike("张%");
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * Repository--@Query测试
     */
    @Test
    public void testQueryByNameUseHQL() {
        List<Users> list = this.usersRepositoryQueryAnnotation.queryByNameUseHQL("张三");
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * Repository--@Query测试
     */
    @Test
    public void testQueryByNameUseSQL() {
        List<Users> list = this.usersRepositoryQueryAnnotation.queryByNameUseSQL("张三");
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * Repository--@Query测试
     */
    @Test
    @Transactional //@Transactional与@Test 一起使用时 事务是自动回滚的。
    @Rollback(false) //取消自动回滚
    public void testUpdateUsersNameById() {
        this.usersRepositoryQueryAnnotation.updateUsersNameById("张三三", 1);
    }

    /**
     * CrudRepository测试
     */
    @Test
    public void testCrudRepositorySave() {
        Users user = new Users();
        user.setAddress("天津");
        user.setAge(32);
        user.setName("张三丰");
        this.usersRepositoryCrudRepository.save(user);
    }

    /**
     * CrudRepository测试
     */
    @Test
    public void testCrudRepositoryUpdate() {
        Users user = new Users();
        user.setId(4);
        user.setAddress("南京");
        user.setAge(40);
        user.setName("张三丰");
        this.usersRepositoryCrudRepository.save(user);
    }

    /**
     * CrudRepository测试
     */
    @Test
    public void testCrudRepositoryFindOne() {
        Optional<Users> users = this.usersRepositoryCrudRepository.findById(4);
        System.out.println(users);
    }

    /**
     * CrudRepository测试
     */
    @Test
    public void testCrudRepositoryFindAll() {
        List<Users> list = (List<Users>) this.usersRepositoryCrudRepository.findAll();
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * CrudRepository测试
     */
    @Test
    public void testCrudRepositoryDeleteById() {
        this.usersRepositoryCrudRepository.deleteById(4);

    }

    /**
     * PagingAndSortingRepository   排序测试
     */
    @Test
    public void testPagingAndSortingRepositorySort() {
        //Order 定义排序规则
        //Sort对象封装了排序规则
        List<Users> list = (List<Users>) this.usersRepositoryPagingAndSorting.findAll(Sort.by(Sort.Direction.DESC, "id"));
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * PagingAndSortingRepository   分页测试
     */
    @Test
    public void testPagingAndSortingRepositoryPaging() {
        //Pageable:封装了分页的参数，当前页，每页显示的条数。注意：他的当前页是从0开始。
        //PageRequest(page,size) page:当前页。size:每页显示的条数
        Page<Users> page = this.usersRepositoryPagingAndSorting.findAll(PageRequest.of(1, 2));
        System.out.println("总条数：" + page.getTotalElements());
        System.out.println("总页数" + page.getTotalPages());
        List<Users> list = page.getContent();
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * PagingAndSortingRepository   排序+分页
     */
    @Test
    public void testPagingAndSortingRepositorySortAndPaging() {

        Page<Users> page = this.usersRepositoryPagingAndSorting.findAll(PageRequest.of(1, 3, Sort.by(Sort.Direction.DESC, "id")));
        System.out.println("总条数：" + page.getTotalElements());
        System.out.println("总页数" + page.getTotalPages());
        List<Users> list = page.getContent();
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * JapRepository   排序测试
     */
    @Test
    public void testJpaRepositorySort() {
        //Order 定义排序规则
        //Sort对象封装了排序规则
        List<Users> list = this.usersRepository.findAll(Sort.by(Direction.DESC, "id"));
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * JpaSpecificationExecutor   单条件测试
     */
    @Test
    public void testJpaSpecificationExecutor1() {

        // Specification<Users>:用于封装查询条件
        Specification<Users> spec = new Specification<Users>() {

            //Predicate:封装了 单个的查询条件

            /**
             * Root<Users> root:查询对象的属性的封装。
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
             * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
             */
            @Override
            public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                /*
                  where name = '张三三'
                  参数一：查询的条件属性
                  参数二：条件的值
                 */
                return cb.equal(root.get("name"), "张三三");
            }
        };
        List<Users> list = this.usersRepositorySpecification.findAll(spec);
        for (Users users : list) {
            System.out.println(users);
        }
    }


    /**
     * JpaSpecificationExecutor   多条件测试
     */
    @Test
    public void testJpaSpecificationExecutor2() {

        // Specification<Users>:用于封装查询条件
        Specification<Users> spec = new Specification<Users>() {

            /**
             *
             * Predicate:封装了 单个的查询条件
             *
             *
             * Root<Users> root:查询对象的属性的封装。
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
             * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
             */
            @Override
            public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // where name = '张三三' and age = 20
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("name"), "张三三"));
                list.add(cb.equal(root.get("age"), 20));
                Predicate[] arr = new Predicate[list.size()];
                return cb.and(list.toArray(arr));
            }
        };
        List<Users> list = this.usersRepositorySpecification.findAll(spec);
        for (Users users : list) {
            System.out.println(users);
        }
    }

    /**
     * JpaSpecificationExecutor   多条件测试第二种写法
     */
    @Test
    public void testJpaSpecificationExecutor3() {

        //Specification<Users>:用于封装查询条件
        Specification<Users> spec = new Specification<Users>() {
            /**
             * Predicate:封装了 单个的查询条件
             *
             * Root<Users> root:查询对象的属性的封装。
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
             * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
             */
            @Override
            public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // where name = '张三三' and age = 20
                /*List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("name"),"张三三"));
                list.add(cb.equal(root.get("age"),20));
                Predicate[] arr = new Predicate[list.size()];*/
                //(name = '张三' and age = 20) or id = 2
                return cb.or(cb.and(cb.equal(root.get("name"), "张三三"), cb.equal(root.get("age"), 20)), cb.equal(root.get("id"), 2));
            }
        };

        List<Users> list = this.usersRepositorySpecification.findAll(spec, Sort.by(Sort.Direction.DESC, "id"));
        for (Users users : list) {
            System.out.println(users);
        }
    }
}

