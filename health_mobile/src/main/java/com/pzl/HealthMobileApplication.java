package com.pzl;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
//添加对Dubbo的支持
@EnableDubbo
public class HealthMobileApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthMobileApplication.class, args);
    }

}
