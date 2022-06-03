package com.example.book.interceptor;


import com.example.book.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Component
public class SessionTimeoutInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // 从session中获取用户信息
        User loginUser = (User) session.getAttribute("loginUser");

        // session过期
        if(loginUser == null){
            // 通过接口跳转登录页面
            response.sendRedirect("/login");
            return false;
        }else{
            return true;
        }
    }
}
