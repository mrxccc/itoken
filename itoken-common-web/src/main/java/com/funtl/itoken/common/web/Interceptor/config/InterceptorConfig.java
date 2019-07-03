package com.funtl.itoken.common.web.Interceptor.config;

import com.funtl.itoken.common.web.Interceptor.ContantsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: BernardLowe
 * @Description:
 * @Date: Created in 23:33 2019/6/19
 * @Modified By: BernardLowe
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ContantsInterceptor()).addPathPatterns("/**");
    }
}
