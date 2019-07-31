package com.ssm.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截
        //排除不需要拦截路径
        if (request.getRequestURI().endsWith("managerController/managerLogin.do")){
            return true;
        }
//        如果已经登录,放行
        if (request.getSession().getAttribute("manager")!=null){
            return true;
        }
        request.getRequestDispatcher("/WEB-INF/views/login.html").forward(request,response);
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("未返回视图前 后处理.......");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("返回视图前 后处理.......");
    }
}
