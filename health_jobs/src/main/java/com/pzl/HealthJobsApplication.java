package com.pzl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //开启基于注解的定时任务
public class HealthJobsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthJobsApplication.class, args);
    }

}
