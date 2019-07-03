package com.funtl.itoken.common.web.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: BernardLowe
 * @Description: 初始化常量拦截器
 * @Date: Created in 23:27 2019/6/19
 * @Modified By: BernardLowe
 */
public class ContantsInterceptor implements HandlerInterceptor {
    private static final String HOST_CDN = "http://192.168.17.130";

    private static final String TEMPLATE_ADMIN_LET = "adminlte/v2.4.3";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView!=null){
            modelAndView.addObject("adminlte",HOST_CDN+"/"+TEMPLATE_ADMIN_LET);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
