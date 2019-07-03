package com.funtl.itoken.service.sso.controller;

import com.funtl.itoken.common.domain.TbSysUser;
import com.funtl.itoken.common.utils.CookieUtils;
import com.funtl.itoken.common.utils.MapperUtils;
import com.funtl.itoken.common.utils.StringUtils;
import com.funtl.itoken.service.sso.service.LoginService;
import com.funtl.itoken.service.sso.service.consumer.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: BernardLowe
 * @Description:
 * @Date: Created in 15:41 2019/6/18
 * @Modified By: BernardLowe
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = {"","login"}, method = RequestMethod.GET)
    public String login(@RequestParam(required = false) String url, HttpServletRequest httpServletRequest, Model model){
        String token = CookieUtils.getCookieValue(httpServletRequest,"token");

        if(StringUtils.isNotBlank(token)){
            String loginCode = redisService.get(token);
            if(StringUtils.isNotBlank(loginCode)){
                String json =redisService.get(loginCode);
                if(StringUtils.isNotBlank(json)){
                    try {
                        TbSysUser tbSysUser = MapperUtils.json2pojo(json,TbSysUser.class);
                        if(tbSysUser!=null){
                            if(StringUtils.isNotBlank(url)){
                                return "redirect:"+url;
                            }
                        }
                        model.addAttribute("tbSysUser",tbSysUser);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(StringUtils.isNotBlank(url)){
            model.addAttribute("url",url);
        }

        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login( @RequestParam(required = true) String loginCode, @RequestParam(required = true)String password, @RequestParam(required = false) String url,
                         HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         RedirectAttributes redirectAttributes){
        TbSysUser tbSysUser = loginService.login(loginCode,password);
        if(tbSysUser == null) {
            redirectAttributes.addFlashAttribute("message","用户名或密码错误");
        }
        else {
            String token = UUID.randomUUID().toString();
            String result = redisService.put(token, loginCode, 60 * 60 * 24);
            if (StringUtils.isNotBlank(result) && "ok".equals(result)) {
                CookieUtils.setCookie(httpServletRequest, httpServletResponse, "token", token, 60 * 60 * 24);
                if (StringUtils.isNotBlank(url)) {
                    return "redirect:" + url;
                }
            }
            // 熔断
            else {
                redirectAttributes.addFlashAttribute("message","服务器异常，请稍后再试");
            }
        }
        return "redirect:/login";
    }

    /**
     * @Description: 注销
     * @auther: BernardLowe
     * @date: 14:17 2019/6/20
     * @param: [request, response, url, model]
     * @return: java.lang.String
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout( HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false) String url,Model model){
        try {
            CookieUtils.deleteCookie(request,response,"token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login(url,request,model);
    }
}
