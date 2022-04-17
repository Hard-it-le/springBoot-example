package com.yjl.basic.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/17
 */
//自定义注解类
@Component
//指定自定义配置文件位置和名称
@PropertySource("classpath:test.properties")
//指定配置文件注入属性前缀
@ConfigurationProperties(prefix = "test")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyProperties {

    private int id;

    private String name;
}
