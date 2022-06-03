package com.example.book.service;


import com.example.book.entity.User;
import com.example.book.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserService {

    @Resource
    public UserMapper userMapper;

    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    public void modify(User user) {
        userMapper.update(user);
    }

}
