package com.meteor.jsherp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.meteor.jsherp.mapper")
public class JshErpSelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(JshErpSelfApplication.class, args);
    }

}
