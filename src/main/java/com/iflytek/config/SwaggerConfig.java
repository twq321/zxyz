package com.iflytek.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // TODO: 修改为你实际的基础包路径，只扫描该包下的 Controller 类
                .apis(RequestHandlerSelectors.basePackage("com.iflytek"))
                // 排除 Spring Boot 默认的 /error 端点
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("智行驿站后端 API 文档")
                .description("")       // 可选：接口描述
                .version("1.1")        // 版本信息
                .termsOfServiceUrl("") // 服务条款 URL（可选）
                .contact(new Contact(
                        "twq",
                        "http://www.ccsfu.edu.cn",
                        "3276694904@qq.com"
                ))
                .license("")           // 许可证
                .licenseUrl("")        // 许可证 URL
                .build();
    }
}
