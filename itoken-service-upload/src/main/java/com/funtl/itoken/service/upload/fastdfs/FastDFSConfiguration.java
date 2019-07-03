package com.funtl.itoken.service.upload.fastdfs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: BernardLowe
 * @Description: Java 配置方式定义 StorageFactory 的 Bean 使其可以被依赖注入
 * @Date: Created in 16:24 2019/6/29
 * @Modified By: BernardLowe
 */
@Configuration
public class FastDFSConfiguration {
    @Bean
    public StorageFactory storageFactory() {
        return new StorageFactory();
    }
}
