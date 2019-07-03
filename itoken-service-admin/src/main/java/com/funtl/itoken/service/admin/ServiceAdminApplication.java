package com.funtl.itoken.service.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: BernardLowe
 * @Description:
 * @Date: Created in 17:40 2019/6/3
 * @Modified By: BernardLowe
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.funtl.itoken.service.admin.mapper","com.funtl.itoken.common.mapper"})
public class ServiceAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAdminApplication.class, args);
    }
}

