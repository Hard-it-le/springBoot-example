package com.yjl.jpa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
@Entity
@Table(name = "users")
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address")
    private String address;

    @ManyToOne(cascade = CascadeType.PERSIST)
    /* @JoinColumn:维护外键 */
    @JoinColumn(name = "roles_id")
    private Roles roles;

}