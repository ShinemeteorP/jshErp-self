package com.meteor.jsherp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @author 刘鑫
 * @version 1.0
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("其余接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.meteor.jsherp.controller"))
                .paths(PathSelectors.regex("^((?!/userBusiness).)*$"))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("华夏ERP Restful API")
                .description("华夏ERP项目APi接口说明文档")
                .termsOfServiceUrl("")
                .contact(new Contact("meteor", "", "2531725036@qq.com"))
                .version("1.0")
                .build();
    }
    @Bean
    public Docket roleDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("权限管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.meteor.jsherp.controller"))
                .paths(PathSelectors.ant("/**/userBusiness/**"))
                .build();


    }
}
