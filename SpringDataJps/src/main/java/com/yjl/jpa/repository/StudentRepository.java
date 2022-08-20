package com.yjl.jpa.repository;

import com.yjl.jpa.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author yujiale
 * @Date 2022/8/11 21:37
 * @Description TODO
 **/
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
    @Query("FROM Student WHERE name like ?1")
    List<Student> findStudentsByName(String name);

    @Query("from Student where sex = ?2 and name like ?1")
    List<Student> findStudentsByNameAndSex(String name, String sex);

    @Query("UPDATE Student set name = ?2 where id = ?1")
    @Modifying
    void updateStudentName(Integer id, String newName);

    @Query(nativeQuery = true, value = "select * from bz_student")
    List<Student> findAllStudents();

    @Query(nativeQuery = true, value = "update bz_student set age = ?2 where id= ?1")
    @Modifying
    void updateStudentAge(Integer id, Integer newAge);


    List<Student> findBySex(String sex);

    List<Student> findBySexAndAge(String sex, Integer age);

    List<Student> findByNameLike(String name);

    List<Student> findByAgeBetween(Integer age1, Integer age2);

    List<Student> findByNameLikeOrAgeBetween(String name, Integer age1, Integer age2);

    Page<Student> findByNameLike(String name, Pageable pageable);

    List<Student> findByNameLike(String name, Sort sort);
}
