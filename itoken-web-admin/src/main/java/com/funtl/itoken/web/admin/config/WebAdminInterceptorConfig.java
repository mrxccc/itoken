package com.funtl.itoken.web.admin.config;

import com.funtl.itoken.web.admin.interceptor.WebAdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: BernardLowe
 * @Description:
 * @Date: Created in 0:14 2019/6/20
 * @Modified By: BernardLowe
 */
@Configuration
public class WebAdminInterceptorConfig implements WebMvcConfigurer {
    @Bean
    WebAdminInterceptor webAdminInterceptor() {
        return new WebAdminInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webAdminInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/static");
    }
}
