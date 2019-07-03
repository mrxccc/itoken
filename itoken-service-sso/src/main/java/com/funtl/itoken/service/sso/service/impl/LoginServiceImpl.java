package com.funtl.itoken.service.sso.service.impl;

import com.funtl.itoken.common.domain.TbSysUser;
import com.funtl.itoken.common.utils.MapperUtils;
import com.funtl.itoken.service.sso.mapper.TbSysUserMapper;
import com.funtl.itoken.service.sso.service.LoginService;
import com.funtl.itoken.service.sso.service.consumer.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;


/**
 * @Author: BernardLowe
 * @Description:
 * @Date: Created in 15:15 2019/6/18
 * @Modified By: BernardLowe
 */
@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public TbSysUser login(String loginCode, String plantPassword) {
        String json = redisService.get(loginCode);
        TbSysUser tbSysUser = null;
        // 缓存中没有数据
        if (json == null) {
            // 查询数据库
            Example example = new Example(TbSysUser.class);
            example.createCriteria().andEqualTo("loginCode", loginCode);

            tbSysUser = tbSysUserMapper.selectOneByExample(example);
            if (tbSysUser != null) {
                String password = DigestUtils.md5DigestAsHex(plantPassword.getBytes());
                if (password.equals(tbSysUser.getPassword())) {
                    // 放入缓存中
                    try {
                        redisService.put(loginCode,MapperUtils.obj2json(tbSysUser),60*60*24);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return tbSysUser;
                }

                // 密码错误
                else {
                    return null;
                }
            }
        }
        // 缓存中有数据
        else {
            try {
                tbSysUser = MapperUtils.json2pojo(json, TbSysUser.class);
            } catch (Exception e) {
                logger.warn("触发熔断 {}",e.getMessage());
            }

        }



        return tbSysUser;
    }
}
