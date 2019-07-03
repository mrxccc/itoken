package com.funtl.itoken.service.sso.service;

import com.funtl.itoken.common.domain.TbSysUser;

/**
 * @Author: BernardLowe
 * @Description:
 * @Date: Created in 15:13 2019/6/18
 * @Modified By: BernardLowe
 */
public interface LoginService {

    /**
     * @Description: 登录
     * @auther: BernardLowe
     * @date: 15:14 2019/6/18
     * @param: [loginCode, plantPassword]
     * @return: com.funtl.itoken.common.domain.TbSysUser
     */
    public TbSysUser login(String loginCode,String plantPassword);
}
