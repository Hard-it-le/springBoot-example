package com.yjl.jpa.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
@Entity(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String username;

    private String password;


    private int enabled;
}
