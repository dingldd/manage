package com.example.book.controller;

import com.example.book.entity.User;
import com.example.book.framework.Result;
import com.example.book.framework.ResultUtil;
import com.example.book.service.UserService;
import com.example.book.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/modifyPassword")
    public Result<String> modifyPassword(HttpServletRequest request, @RequestBody Map<String,String> param){
        // 校验密码
        String oldPassword = param.get("oldPassword");
        String newPassword = param.get("newPassword");
        String newPassword2 = param.get("newPassword2");
        if(oldPassword.isEmpty()){
            return ResultUtil.fail("原密码不能为空！");
        }
        if(newPassword.isEmpty()){
            return ResultUtil.fail("新密码不能为空！");
        }
        if(newPassword2.isEmpty()){
            return ResultUtil.fail("确认密码不能为空！");
        }
        HttpSession session = request.getSession();
        // 从session中获取用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        String realPassword = loginUser.getPassword();
        if(!(Md5Util.encode(oldPassword).equals(realPassword))){
            return ResultUtil.fail("原密码输入错误！");
        }
        if(!(newPassword.equals(newPassword2))){
            return ResultUtil.fail("新密码输入不一致！");
        }
        loginUser.setPassword(Md5Util.encode(newPassword));
        userService.modify(loginUser);
        return ResultUtil.ok();
    }

}
