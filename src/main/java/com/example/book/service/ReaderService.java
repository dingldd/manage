package com.example.book.service;

import com.example.book.entity.Reader;
import com.example.book.mapper.ReaderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ReaderService {

    @Resource
    ReaderMapper readerMapper;

    public void add(Reader entity) {
        readerMapper.insert(entity);
    }

    public void modify(Reader entity) {
        readerMapper.update(entity);
    }

    public void removes(Integer[] ids) {
        readerMapper.deletes(ids);
    }

    public Reader get(Integer id) {
        return readerMapper.select(id);
    }

//    public String getNameById(Integer id){ return  readerMapper.}

    public int count(Map<String, Object> param) {
        return readerMapper.count(param);
    }

    public List<Reader> getList(Map<String, Object> param) {
        return readerMapper.selectList(param);
    }

    public List<Reader> getPageResult(Map<String, Object> param) {
        return readerMapper.selectPageResult(param);
    }

    public int checkPhone(Reader entity){
        return readerMapper.countPhone(entity);
    }

}
