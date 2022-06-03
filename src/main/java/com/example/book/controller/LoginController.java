package com.example.book.controller;

import com.example.book.entity.User;
import com.example.book.service.UserService;
import com.example.book.utils.Md5Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    public UserService userService;


    @RequestMapping("/admin")
    public String login(HttpServletRequest request ,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model){
        User user=userService.selectUserByUsername(username);
        if(user!=null && Md5Util.encode(password).equals(user.getPassword())){
//          Md5Util.encode(password).equals(user.getPassword());
            HttpSession session= request.getSession();
            session.setAttribute("loginUser",user);
            return "admin";
        }else {
            model.addAttribute("msg","用户名或密码错误！");
            return "login";
        }
    }
}