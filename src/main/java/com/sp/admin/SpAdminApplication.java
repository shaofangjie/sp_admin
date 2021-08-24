package com.sp.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sp.admin.mapper") //扫描的mapper
public class SpAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpAdminApplication.class, args);
    }

}
