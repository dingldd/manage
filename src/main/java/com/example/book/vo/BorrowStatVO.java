package com.example.book.vo;

import lombok.Data;

import java.util.List;

/**
 * VO，值对象(Value Object)，
 * 它们是由一组属性和属性的get和set方法组成。
 * 是存活在业务层的，是业务逻辑使用的
 */
@Data
public class BorrowStatVO {
    private String name;
    private String type;
    private List<Integer> data;
}
