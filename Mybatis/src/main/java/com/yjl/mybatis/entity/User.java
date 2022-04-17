package com.yjl.mybatis.entity;

import lombok.*;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private Integer enabled;
}
