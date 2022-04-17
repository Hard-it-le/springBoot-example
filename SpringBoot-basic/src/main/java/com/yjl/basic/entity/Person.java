package com.yjl.basic.entity;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 * @ConfigurationProperties(prefix = "person")注解的作用是将配置文件中以person开头的属性值通过setXX()方法注入到实体类对应属性中
 * @Component注解的作用是将当前注入属性值的Person类对象作为Bean组件放到Spring容器中,只有这样才能被@ConfigurationProperties注解进行赋值
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private Integer id;
    private String name;
    private List<String> hobby;

    private String[] family;

    private Map map;

    private Pet pet;

}
