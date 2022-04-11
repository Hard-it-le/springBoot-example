package com.yjl.fastjsondemo.controller;


import com.alibaba.fastjson.JSON;
import com.yjl.fastjsondemo.config.FastJsonConfig;
import com.yjl.fastjsondemo.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/11
 */
@RestController
@RequestMapping("/json")
public class JsonController {
    @RequestMapping("/user")
    public User getUser() {
        return new User(1L, "张三", "123456");
    }

    @RequestMapping("/list")
    public List<User> getUserList() {

        List<User> userList = new ArrayList<>();
        User user1 = new User(1L, "张三", "123456");

        User user2 = new User(2L, "李四", "123456");
        userList.add(user1);
        userList.add(user2);
        return userList;
    }

    @RequestMapping("/map")
    public Map<String, Object> getMap() {

        Map<String, Object> map = new HashMap<>(3);
        User user = new User(1L, "张三", "123456");
        map.put("作者信息", user);
        map.put("博客地址", "http://blog.com");
        map.put("CSDN地址", "http://blog.csdn.net/");
        map.put("粉丝数量", 4153);
        return map;
    }

    @Resource
    private FastJsonConfig fastJsonConfig;

    @RequestMapping("/mapNull")
    public Map<String, Object> getMapByNull() {

        Map<String, Object> map = new HashMap<>(3);
        User user = new User(1L, "张三", null);
        map.put("作者信息", user);
        map.put("博客地址", "http://blog.com");
        map.put("CSDN地址", null);
        map.put("粉丝数量", 4153);
        String s = JSON.toJSONString(map);
        map.put("json", s);
        return map;
    }
}
