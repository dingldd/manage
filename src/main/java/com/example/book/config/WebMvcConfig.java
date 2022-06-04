package com.example.book.config;

import com.example.book.interceptor.SessionTimeoutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    SessionTimeoutInterceptor sessionTimeoutInterceptor;
    /**
     * 释放静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/**");
        registry.addResourceHandler("/assets/ckeditor/**").
                addResourceLocations("classpath:/static/assets/ckeditor/").
                setCachePeriod(2592000);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/book").setViewName("book");
        registry.addViewController("/bookAdd").setViewName("bookAdd");
        registry.addViewController("/bookEdit").setViewName("bookEdit");
        registry.addViewController("/bookBorrow").setViewName("bookBorrow");
        registry.addViewController("/bookBorrowAdd").setViewName("bookBorrowAdd");
        registry.addViewController("/bookBorrowEdit").setViewName("bookBorrowEdit");
        registry.addViewController("/bookReBorrow").setViewName("bookReBorrow");
        registry.addViewController("/bookReBorrowEdit").setViewName("bookReBorrowEdit");
        registry.addViewController("/bookReturn").setViewName("bookReturn");
        registry.addViewController("/bookReader").setViewName("bookReader");
        registry.addViewController("/bookStat").setViewName("bookStat");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionTimeoutInterceptor).addPathPatterns("/**").
                excludePathPatterns("/api/book/list","/bookDetail/*"
                        ,"/","/index","/login","/admin"
                        ,"/css/**","/img/**","/js/**","/layui/**"
                        ,"/static/**");
    }

}
