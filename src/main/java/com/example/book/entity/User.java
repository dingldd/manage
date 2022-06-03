package com.example.book.entity;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class User extends BaseBean {

    private String username;
    private String password;

}
