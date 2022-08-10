package com.yjl.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author yujiale
 * @Date 2022/8/10 21:08
 * @Description TODO
 **/
@Entity
@Table(name = "t_roles")
@Data
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid")
    private Integer roleid;

    @Column(name = "rolename")
    private String rolename;

    @OneToMany(mappedBy = "roles")
    private Set<Users> users = new HashSet<>();

}