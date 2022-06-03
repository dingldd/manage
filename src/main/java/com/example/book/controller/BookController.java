package com.example.book.controller;

import com.example.book.entity.Book;
import com.example.book.framework.Result;
import com.example.book.framework.ResultBean;
import com.example.book.framework.ResultUtil;
import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/save")
    public Result<String> modify(@RequestBody Book entity) {
        int count = bookService.checkIsbn(entity);
        if(count>0){
            return ResultUtil.fail("ISBN已存在！");
        }
        if(entity.getId()!=null){
            bookService.modify(entity);
        }else{
            bookService.add(entity);
        }

        return ResultUtil.ok();
    }
    @PostMapping("/remove")
    public Result<String> remove(@RequestBody Integer[] ids) {
        bookService.removes(ids);
        return ResultUtil.ok();
    }

    @GetMapping("/list")
    public ResultBean<List<Book>> getPageResult(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {

        Map<String, Object> param = new HashMap<>(16);

        // 计算起始行号
        int offset = (page - 1) * limit;
        int rows = limit;

        param.put("name",name);
        param.put("isbn",isbn);
        param.put("author",author);
        param.put("offset", offset);
        param.put("rows", rows);

        // 统计记录数
        int totalRows = bookService.count(param);

        // 获取当前页结果集
        List<Book> entities = bookService.getPageResult(param);

        return new ResultBean<>(0, "查询成功", totalRows, entities);

    }


}
