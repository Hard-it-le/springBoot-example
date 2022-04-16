package com.yjl.basic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/16
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/demo01")
    public String testDemo() {

        return "测试成功";


    }

}
