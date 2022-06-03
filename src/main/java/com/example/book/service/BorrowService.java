package com.example.book.service;


import com.example.book.mapper.BorrowMapper;
import com.example.book.mapper.ReaderMapper;
import com.example.book.vo.BorrowVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class BorrowService {

    @Resource
    public BorrowMapper borrowMapper;
    @Resource
    public ReaderMapper readerMapper;

    public void add(BorrowVO entity) {
        borrowMapper.insert(entity);
    }

    public void modify(BorrowVO entity) {
        borrowMapper.update(entity);
    }

    public void removes(Integer[] ids) {
        borrowMapper.deletes(ids);
    }

    public void returnBook(Map<String, Object> param) {
        borrowMapper.returnBook(param);
    }

    public BorrowVO get(Integer id) {
        return borrowMapper.select(id);
    }

    public int count(Map<String, Object> param) {
        return borrowMapper.count(param);
    }

    public List<BorrowVO> getPageResult(Map<String, Object> param) {
        return borrowMapper.selectPageResult(param);
    }

    public int checkBorrow(BorrowVO entity){
        return borrowMapper.countBorrow(entity);
    }

    public int getBorrowCount(String date){
        return borrowMapper.selectBorrowCount(date);
    }

    public int getReturnCount(String date){
        return borrowMapper.selectReturnCount(date);
    }


    public Double checkFine(Integer readerId) {
      return  readerMapper.checkFine(readerId);
    }

}
