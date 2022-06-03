package com.example.book.vo;

import com.example.book.entity.BaseBean;
import lombok.Data;

import java.util.Date;


@Data
public class BorrowVO extends BaseBean {
    private Integer id;
    private Integer readerId;
    private String readerName;
    private String readerPhone;
    private Integer bookId;
    private String bookIsbn;
    private String bookName;
    private String bookAuthor;
    private String bookCategory;
    private String bookLocation;
    private Integer bookTotal;
    private Integer bookLeft;
    private Date borrowDate;
    private Date returnDate;
    private Date realReturnDate;
    private Integer borrowDays;
    private Integer reBorrowDays;
    private Integer borrowStatus;
}
