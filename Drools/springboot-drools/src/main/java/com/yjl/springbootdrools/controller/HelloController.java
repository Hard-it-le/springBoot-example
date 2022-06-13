package com.yjl.springbootdrools.controller;


import com.yjl.springbootdrools.service.RuleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yujiale
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @Resource
    private RuleService ruleService;

    @RequestMapping("/rule")
    public String rule() {
        ruleService.rule();
        return "OK";
    }
}
