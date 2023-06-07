package com.meteor.jsherp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.meteor.jsherp.mapper")
@ServletComponentScan
public class JshErpSelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(JshErpSelfApplication.class, args);
    }


}
