package com.sy.camunda.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * @Author  YuXunHao
 * @Description  knife4j配置类， 访问url：http://ip:port/doc.html
 * @Date  2023/3/10 14:46
 * @Update
 **/
@EnableKnife4j
@Configuration
public class SwaggerConfiguration {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .globalRequestParameters(
                        Collections.singletonList(new RequestParameterBuilder()
                                .name("token")
                                .in(ParameterType.HEADER)
                                .build()))
                .produces(Collections.singleton("application/json"))
                .consumes(Collections.singleton("application/json")).select()
                .apis(RequestHandlerSelectors.basePackage("com.sy"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("")
                .version("1.0")
                .title("camunda")
                .build();
    }


}
