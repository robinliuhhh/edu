package com.robin.educourseboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient // 注册到中心的客户端
@MapperScan("com.robin.educourseboot.mapper")
@EnableFeignClients // Feign的客户端
public class EduCourseBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduCourseBootApplication.class, args);
    }

}
