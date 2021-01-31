package com.bjlemon.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bjlemon"})
@MapperScan("com.bjlemon.ucenter.mapper")
@EnableDiscoveryClient //开启nacos注册
@EnableFeignClients //开启服务调用
public class UcenterMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterMemberApplication.class,args);
    }
}
