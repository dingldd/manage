package com.example.book.controller;

import com.example.book.entity.Book;
import com.example.book.entity.Reader;
import com.example.book.framework.Result;
import com.example.book.framework.ResultBean;
import com.example.book.framework.ResultUtil;
import com.example.book.service.BookService;
import com.example.book.service.BorrowService;
import com.example.book.service.ReaderService;
import com.example.book.utils.DateUtil;
import com.example.book.vo.BorrowStatVO;
import com.example.book.vo.BorrowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// 该注解是@Controller和@ResponseBody的合集,
// 表示这是个控制器Bean,并且是将函数的返回值直接填入HTTP响应体中,是REST风格的控制器
@RequestMapping("/api/borrow")
// 提供路由信息，负责URL到Controller中的具体函数的映射。
public class BorrowController {
//    自动导入
    @Autowired
    BorrowService borrowService;

    @Autowired
    BookService bookService;

    @Autowired
    ReaderService readerService;

    @GetMapping("/getPageResult")
    public ResultBean<List<BorrowVO>> getPageResult(
            @RequestParam(required = false) Integer[] borrowStatus,
            @RequestParam(required = false) String readerId,
            @RequestParam(required = false) String isbn,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {

        Map<String, Object> param = new HashMap<>(16);

        // 计算起始行号
        int offset = (page - 1) * limit;
        int rows = limit;

        param.put("offset", offset);
        param.put("rows", rows);
        param.put("borrowStatus",borrowStatus);
        param.put("readerId",readerId);
        param.put("isbn",isbn);

        // 统计记录数
        int totalRows = borrowService.count(param);

        // 获取当前页结果集
        List<BorrowVO> entities = borrowService.getPageResult(param);

        return new ResultBean<>(0, "查询成功", totalRows, entities);

    }
    @PostMapping("/show")
    public String show(@RequestBody BorrowVO entity) {
        Integer id = entity.getId();
        Map<String, Object> param = new HashMap<>(16);
        param.put("bookId", entity.getBookId());
        param.put("readerId", entity.getReaderId());
        String name=readerService.get(entity.getReaderId()).getName();
        System.out.println(name);
        return name;
    }

    @PostMapping(value = "/a1",produces ="application/json;charset=utf-8")
    public void showBorrowInfo(int readerId, HttpServletResponse response) throws IOException {
        Reader reader = readerService.get(readerId);
        String readerName = reader.getName();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(readerName);
    }

    @PostMapping("/save")
    public Result<String> modify(@RequestBody BorrowVO entity) {
        Integer id = entity.getId();
        Double fine=0.0;
        if (id != null) {
            borrowService.modify(entity);
        } else {
            Map<String, Object> param = new HashMap<>(16);
            param.put("bookId", entity.getBookId());
            param.put("readerId", entity.getReaderId());
            //1.检查读者编号是否存在，且是否有罚金
            if (readerService.count(param) == 0) {
                return ResultUtil.fail("读者不存在！");
            }else {
                fine = borrowService.checkFine(entity.getReaderId());
                if(fine!=0){
                    return ResultUtil.fail("请交罚款"+fine+"元");
                }
            }
            //2.检查图书编号是否存在
            if (bookService.count(param) == 0) {
                return ResultUtil.fail("图书不存在！");
            }
            //3.检查该读者是否已经借过此书
            int count = borrowService.checkBorrow(entity);
            if (count > 0) {
                return ResultUtil.fail("您已借过该图书！");
            }
            //4.检查图书剩余
            Book book = bookService.getById(entity.getBookId());
            int leftNumber = book.getLeftNumber();
            if (leftNumber < 1) {
                return ResultUtil.fail("图书剩余数量为0！");
            }
            //5.扣减图书剩余
            book.setLeftNumber(leftNumber - 1);
            bookService.modify(book);
            //6.保存借阅记录
            borrowService.add(entity);
        }
        return ResultUtil.ok();
    }


    @PostMapping("/remove")
    public Result<String> remove(@RequestBody Integer[] ids) {
        borrowService.removes(ids);
        return ResultUtil.ok();
    }

//    @PostMapping("/return")
//    @Transactional(rollbackFor=Exception.class)
//    public Result<String> returnBook(@RequestBody Integer[] ids) {
//        // 1.增加图书库存
//        Book entity;
//        for (Integer id: ids) {
//            entity = bookService.getById(borrowService.get(id).getBookId());
//            entity.setLeftNumber(entity.getLeftNumber()+1);
//            bookService.modify(entity);
//        }
//        // 2.更新借阅状态
//        Map<String,Object> param = new HashMap<>(16);
//        param.put("ids", ids);
//        param.put("borrowStatus",1);
//        borrowService.returnBook(param);
//
//        return ResultUtil.ok();
//    }
    @PostMapping("/return")
    public Result<String> returnBook(@RequestBody Integer[] ids){
        Book book = new Book();
        for(Integer id:ids){
            BorrowVO borrowVO = borrowService.get(id);
            Book book1 = bookService.getById(borrowVO.getBookId());
            book.setLeftNumber(book1.getLeftNumber()+1);
            bookService.modify(book);
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ids",ids);
        hashMap.put("borrowStatus",1);
        borrowService.returnBook(hashMap);

      return ResultUtil.ok();
    }


    @GetMapping("/get")
    public Result<BorrowVO> get(@RequestParam("id") Integer id) {
        BorrowVO entity = borrowService.get(id);

        return ResultUtil.ok(entity);
    }

    @GetMapping("/getBorrowStat")
    public Result<Map<String,Object>> getBorrowStat(){
        Map<String,Object> map = new HashMap<>(16);
        List<String> days = DateUtil.getDaysBetwwen(6);

        map.put("columnName",days);
        BorrowStatVO borrowVO = new BorrowStatVO();
        BorrowStatVO returnVO = new BorrowStatVO();
        borrowVO.setName("借");
        returnVO.setName("还");
        borrowVO.setType("bar");
        returnVO.setType("bar");
        List<Integer> borrowData = new ArrayList<>();
        List<Integer> returnData = new ArrayList<>();
        for (String day:days) {
            borrowData.add(borrowService.getBorrowCount(day));
            returnData.add(borrowService.getReturnCount(day));
        }
        borrowVO.setData(borrowData);
        returnVO.setData(returnData);

        List<BorrowStatVO> list = new ArrayList<>();
        list.add(borrowVO);
        list.add(returnVO);

        map.put("columnValue",list);

        return ResultUtil.ok(map);

    }


}
