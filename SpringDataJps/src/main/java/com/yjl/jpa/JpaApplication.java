package com.yjl.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author yujiale
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.yjl.jpa.repository")
@EntityScan("com.yjl.jpa.entity")
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

}
