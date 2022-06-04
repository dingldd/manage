package com.example.book.controller;

import com.example.book.entity.Reader;
import com.example.book.framework.Result;
import com.example.book.framework.ResultBean;
import com.example.book.framework.ResultUtil;
import com.example.book.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reader")
public class ReaderController {

    @Autowired
    ReaderService readerService;

    @PostMapping("/save")
    public Result<String> modify(@RequestBody Reader entity) {
        int count = readerService.checkPhone(entity);
        if(count>0){
            return ResultUtil.fail("手机号已存在！");
        }
        if(entity.getId()!=null){
            readerService.modify(entity);
        }else{
            readerService.add(entity);
        }

        return ResultUtil.ok();
    }


    @PostMapping("/remove")
    public Result<String> remove(@RequestBody Integer[] ids) {
        readerService.removes(ids);
        return ResultUtil.ok();
    }

    @GetMapping("/get")
    public Result<Reader> get(@RequestParam("id") Integer id) {
        Reader entity = readerService.get(id);

        return ResultUtil.ok(entity);
    }

    @GetMapping("/list")
    public ResultBean<List<Reader>> list(){
        List<Reader> entities = readerService.getList(null);
        return new ResultBean(0, "查询成功", entities.size(), entities);
    }

    @GetMapping("/getPageResult")
    public ResultBean<List<Reader>> getPageResult(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {

        Map<String, Object> param = new HashMap(16);
        // 统计记录数
        int totalRows = readerService.count(param);
        // 计算起始行号
        int offset = (page - 1) * limit;
        int rows = limit;

        param.put("offset", offset);
        param.put("rows", rows);

        // 获取当前页结果集
        List<Reader> entities = readerService.getPageResult(param);

        return new ResultBean(0, "查询成功", totalRows, entities);

    }
}
