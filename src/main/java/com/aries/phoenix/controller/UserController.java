package com.aries.phoenix.controller;

import com.aries.phoenix.mapper.UserMapper;
import com.aries.phoenix.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserMapper userMapper;

    @RequestMapping("/getUser")
    public User getUser(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @RequestMapping("/get")
    public String gUser() {
        return "123";
    }
}