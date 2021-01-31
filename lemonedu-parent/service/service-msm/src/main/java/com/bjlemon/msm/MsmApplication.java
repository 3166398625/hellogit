package com.bjlemon.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bjlemon"})
@EnableDiscoveryClient //开启nacos注册
@EnableFeignClients //开启服务调用
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class,args);
    }
}
