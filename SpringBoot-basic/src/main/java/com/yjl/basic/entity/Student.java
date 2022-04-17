package com.yjl.basic.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Value("${person.id}")
    private int id;

    @Value("${person.name}")
    private String name;
}
