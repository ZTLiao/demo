package com.ztliao.shardingjdbcdemo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ztliao.shardingjdbcdemo.entity.User;
import com.ztliao.shardingjdbcdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: liaozetao
 * @date: 2021/3/12 10:11 PM
 * @description:
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/select")
    public List<User> select() {
        return userMapper.selectList(Wrappers.emptyWrapper());
    }
}
