package com.example.book.entity;

import lombok.Data;


@Data
public class Book extends BaseBean{
    /**
     * 图书名称
     */
    private String name;
    /**
     * 图书编号（ISBN）
     */
    private String isbn;
    /**
     * 图书分类名称
     */
    private String categoryName;
    /**
     * 作者
     */
    private String author;
    /**
     * 出版社名称
     */
    private String publisherName;
    /**
     * 单价（元）
     */
    private Double price;
    /**
     * 藏书数量
     */
    private Integer totalNumber;
    /**
     * 馆内剩余
     */
    private Integer leftNumber;
    /**
     * 内容简介
     */
    private String description;
    /**
     * 书架位置
     */
    private String location;
}
