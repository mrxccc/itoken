package com.funtl.itoken.web.posts.config;

import com.funtl.itoken.web.posts.interceptor.WebPostsInterceptor;
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
public class WebPostsInterceptorConfig implements WebMvcConfigurer {
    @Bean
    WebPostsInterceptor webPostsInterceptor() {
        return new WebPostsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webPostsInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/static");
    }
}
