package com.bjlemon.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication //(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.bjlemon"})
@MapperScan("com.bjlemon.eduorder.mapper")
@EnableDiscoveryClient
@EnableFeignClients //开启服务调用
public class EduOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduOrderApplication.class,args);
    }
}
