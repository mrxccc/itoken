package com.funtl.itoken.web.posts.interceptor;

import com.funtl.itoken.common.domain.TbSysUser;
import com.funtl.itoken.common.utils.CookieUtils;
import com.funtl.itoken.common.utils.MapperUtils;
import com.funtl.itoken.common.utils.StringUtils;
import com.funtl.itoken.common.web.constants.WebConstants;
import com.funtl.itoken.common.web.utils.HttpServletUtils;
import com.funtl.itoken.web.posts.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: BernardLowe
 * @Description:
 * @Date: Created in 0:12 2019/6/20
 * @Modified By: BernardLowe
 */
public class WebPostsInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Value(value = "${hosts.sso}")
    private String host_sso;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, WebConstants.SESSION_TOKEN);

        if(StringUtils.isBlank(token)){
            try {
                //http://localhost:8503/login?url=http://localhost:8601
                response.sendRedirect(String.format("%s/login?url=%s",host_sso, HttpServletUtils.getFullPath(request)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        TbSysUser tbSysUser = (TbSysUser) session.getAttribute(WebConstants.SESSION_USER);

        if(tbSysUser != null ){
            if (modelAndView != null) {
                modelAndView.addObject(WebConstants.SESSION_USER,tbSysUser);
            }
        }

        else{
            String token = CookieUtils.getCookieValue(request, WebConstants.SESSION_TOKEN);
            if(StringUtils.isNotBlank(token)){
                String loginCode = redisService.get(token);
                if(StringUtils.isNotBlank(loginCode)){
                    String json =redisService.get(loginCode);
                    if(StringUtils.isNotBlank(json)){
                        tbSysUser = MapperUtils.json2pojo(json, TbSysUser.class);
                        if (modelAndView != null) {
                            modelAndView.addObject(WebConstants.SESSION_USER,tbSysUser);
                        }
                        request.getSession().setAttribute(WebConstants.SESSION_USER,tbSysUser);
                    }
                }
            }
        }
        if(tbSysUser == null){
            try {
                response.sendRedirect(String.format("%s/login?url=%s",host_sso, HttpServletUtils.getFullPath(request)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
