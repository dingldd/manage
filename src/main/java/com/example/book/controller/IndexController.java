package com.example.book.controller;


import com.example.book.vo.BorrowVO;
import com.example.book.entity.Book;
import com.example.book.service.BookService;
import com.example.book.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
    @Autowired
    BookService bookService;

    @Autowired
    BorrowService borrowService;

    @GetMapping("/bookEdit/{id}")
    public String bookEdit(@PathVariable Integer id, Model model){
        Book book = bookService.get(id);
        model.addAttribute("book",book);

        return "bookEdit";
    }


    @GetMapping("/bookDetail/{id}")
    public String book(@PathVariable Integer id, Model model){
        Book book = bookService.get(id);
        model.addAttribute("book",book);

        return "bookDetail";
    }


    @GetMapping("/bookBorrowEdit/{id}")
    public String bookBorrowEdit(@PathVariable Integer id, Model model){
        BorrowVO bookBorrow = borrowService.get(id);
        model.addAttribute("bookBorrow",bookBorrow);

        return "bookBorrowEdit";
    }


    @GetMapping("/bookReBorrowEdit/{id}")
    public String bookReBorrowEdit(@PathVariable Integer id, Model model){
        BorrowVO bookBorrow = borrowService.get(id);
        model.addAttribute("bookBorrow",bookBorrow);

        return "bookReBorrowEdit";
    }
}
