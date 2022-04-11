package com.yjl.slf4j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/11
 */
@RestController
@RequestMapping("/log")
public class LogController {

    private final static Logger logger = LoggerFactory.getLogger(LogController.class);


    @RequestMapping("/test")
    public String logTest() {
        logger.debug("=====测试日志debug级别打印====");

        logger.info("======测试日志info级别打印=====");

        logger.error("=====测试日志error级别打印====");

        logger.warn("======测试日志warn级别打印=====");


        String str1 = "blog..com";

        String str2 = "blog.csdn.net/";

        logger.info("======努力的it小胖子的个人博客：{}；努力的it小胖子的CSDN博客：{}", str1, str2);

        return "success";
    }

}
