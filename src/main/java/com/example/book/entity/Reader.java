package com.example.book.entity;


import lombok.Data;

@Data
public class Reader extends BaseBean{
    private String name;
    private String phone;
    private double fine;
}
