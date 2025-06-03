package com.iflytek;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@MapperScan("com.iflytek.mapper")
public class MainApplication  {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}