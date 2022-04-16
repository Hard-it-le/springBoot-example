package com.yjl.basic.entity;

import lombok.*;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/16
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private Long id;

    private String username;

    private String password;

}
