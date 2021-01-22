package com.gn.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
//@EnableFeignClients(basePackages = {"com.sibecommon.feign"})
@SpringBootApplication
@MapperScan({"com.gn.web.sys.mapper"})
//@ComponentScan({"com.gn.web", "com.sibecommon"})
public class GnWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GnWebApplication.class, args);
    }

}
