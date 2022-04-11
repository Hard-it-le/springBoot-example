package com.yjl.fastjsondemo.entity;

import lombok.*;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/11
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password;
}
