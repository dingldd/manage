package com.example.book.mapper;

import com.example.book.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    /**
     * 通过姓名查找用户
     * @param username
     * @return User
     */
    User selectUserByUsername(String username);

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user);
}
