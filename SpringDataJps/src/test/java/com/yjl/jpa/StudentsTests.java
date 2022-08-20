package com.yjl.jpa;

import com.yjl.jpa.entity.Student;
import com.yjl.jpa.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author yujiale
 * @Date 2022/8/11 21:41
 * @Description TODO
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentsTests {

    @Resource
    private StudentRepository studentRepository;

    @Test
    public void t1() {
        Student student = new Student(2, "美羊羊", 8, "女");
        studentRepository.save(student);

        Student student01 = new Student(1, "喜羊羊", 8, "男");
        studentRepository.save(student);

        studentRepository.deleteById(1);
        Optional<Student> byId = studentRepository.findById(1);
        Student student02 = byId.get();
        System.out.println(student01);
        System.out.println(student02);
    }

    @Test
    public void t2() {
        long count = studentRepository.count();
        System.out.println(count);
        System.out.println(studentRepository.existsById(1));
        System.out.println(studentRepository.existsById(2));
    }

    @Test
    @Transactional
    public void t3() {
        Optional<Student> byId = studentRepository.findById(1);
        Student one = studentRepository.getOne(1);
        System.out.println(one);
    }

    @Test
    public void t4() {
        System.out.println(studentRepository.findStudentsByName("%羊%"));
        System.out.println(studentRepository.findStudentsByNameAndSex("%羊%", "女"));
    }

    @Test
    @Transactional
    @Rollback(false)
    public void t5() {
        studentRepository.updateStudentName(1, "喜羊羊2");
        studentRepository.updateStudentAge(1, 50);
    }

    @Test
    public void t6() {
        System.out.println(studentRepository.findAllStudents());
    }

    @Test
    public void t7() {
        System.out.println(studentRepository.findBySex("男"));
        System.out.println(studentRepository.findBySexAndAge("男", 10));
        System.out.println(studentRepository.findByNameLike("%羊%"));
        System.out.println(studentRepository.findByAgeBetween(0, 20));
        // 查询一个姓名带喜，或年龄10~20岁的学生
        System.out.println(studentRepository.findByNameLikeOrAgeBetween("%喜%", 10, 20));
    }

    @Test
    public void t8() {
        String querySex = "男";
        Specification<Student> specification = new Specification<Student>() {
            /**
             * 该方法会拼接一个查询条件对象
             * root：获取查询的属性
             * cb：构造查询条件
             */
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 使用root获取查询的字段
                Path<Object> sex = root.get("sex");
                // 使用cb构造查询条件
                Predicate predicate = cb.equal(sex, querySex);
                return predicate;
            }
        };
        List<Student> all = studentRepository.findAll(specification);
        System.out.println(all);
    }

    @Test
    public void t9() {
        String querySex = "男";
        Integer queryAge = 50;
        Specification<Student> specification = new Specification<Student>() {
            /**
             * 该方法会拼接一个查询条件对象
             * root：获取查询的属性
             * cb：构造查询条件
             */
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 使用root获取查询的字段
                Path<Object> sex = root.get("sex");
                Path<Object> age = root.get("age");
                // 使用cb构造查询条件
                Predicate predicate1 = cb.equal(sex, querySex);
                Predicate predicate2 = cb.equal(age, queryAge);
                Predicate predicate = cb.and(predicate1, predicate2);
                return predicate;
            }
        };
        List<Student> all = studentRepository.findAll(specification);
        System.out.println(all);
    }

    @Test
    public void t13() {
        String queryName = "%羊%";
        String querySex = "男";
        Integer queryAge = 50;
        Specification<Student> specification = (root, query, cb) -> {
            Path<String> name = root.get("name");
            Path<String> sex = root.get("sex");
            Path<Integer> age = root.get("age");
            List<Predicate> predicateList = new ArrayList<>();
            if (queryName != null) {
                Predicate predicate1 = cb.like(name, queryName);
                predicateList.add(predicate1);
            }
            if (querySex != null) {
                Predicate predicate2 = cb.equal(sex, querySex);
                predicateList.add(predicate2);
            }
            if (queryAge != null) {
                Predicate predicate3 = cb.equal(age, queryAge);
                predicateList.add(predicate3);
            }
            // 把条件集合转为一个数组
            Predicate[] array = predicateList.toArray(new Predicate[predicateList.size()]);
            Predicate predicate = cb.and(array);
            return predicate;
        };

        System.out.println(studentRepository.findAll(specification));
    }

    @Test
    public void t14() {
        Pageable pageable = PageRequest.of(0, 2);  // 页数是从0开始
        Page<Student> page = studentRepository.findAll(pageable);
        System.out.println(page.getTotalElements()); // 总条数
        System.out.println(page.getTotalPages()); // 总页数
        System.out.println(page.getSize()); // 每页条数
        System.out.println(page.getNumber()); // 当前页码
        System.out.println(page.getContent()); // 数据
    }

    @Test
    public void t15() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Student> all = studentRepository.findAll(sort);
        System.out.println(all);
    }

    // 首先按照年龄正序，如果年龄相同，按照id倒序
    @Test
    public void t16() {
        Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "age");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "id");

        Sort sort = Sort.by(order1, order2);
        System.out.println(studentRepository.findAll(sort));
    }

    @Test
    public void t17() {
        Pageable pageable = PageRequest.of(0, 2);

        Page<Student> page = studentRepository.findByNameLike("%美%", pageable);
        System.out.println(page.getTotalElements()); // 总条数
        System.out.println(page.getTotalPages()); // 总页数
        System.out.println(page.getSize()); // 每页条数
        System.out.println(page.getNumber()); // 当前页码
        System.out.println(page.getContent()); // 数据
    }

    @Test
    public void t18() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        List<Student> students = studentRepository.findByNameLike("%羊%", sort);
        System.out.println(students);
    }
}
