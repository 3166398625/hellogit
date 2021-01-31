package com.bjlemon.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.bjlemon"})
@MapperScan("com.bjlemon.educms.mapper")
@EnableDiscoveryClient
@EnableFeignClients //开启服务调用
public class EducmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EducmsApplication.class,args);
    }
}
