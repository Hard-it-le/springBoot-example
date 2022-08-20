package com.yjl.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author yujiale
 * @Date 2022/8/11 21:36
 * @Description TODO
 **/
@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private Integer id;
    private String name;
    private Integer age;
    private String sex;


}
