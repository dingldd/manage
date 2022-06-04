package com.example.book.service;

import com.example.book.entity.Book;
import com.example.book.mapper.BookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class BookService {

    @Resource
    public BookMapper bookMapper;

    public void add(Book book) {
        bookMapper.insert(book);
    }

    public Integer checkIsbn(Book book) {
        return bookMapper.countIsbn(book);
    }

    public void removes(Integer[] ids) {
        bookMapper.deletes(ids);
    }

    public Book get(Integer id) {
        return bookMapper.select(id);
    }

    public int count(Map<String, Object> param) {
        return bookMapper.count(param);
    }

    public List<Book> getPageResult(Map<String, Object> param) {
        return bookMapper.selectPageResult(param);
    }

    public Book getByIsbn(String bookIsbn) {
        return bookMapper.selectByIsbn(bookIsbn);
    }

    public Book getById(Integer id) {
        return bookMapper.selectById(id);
    }

    public void modify(Book book) {
        bookMapper.update(book);
    }
}
