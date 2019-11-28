package com.github.canglan.cm.identity.config;

import com.github.canglan.cm.common.properties.Swagger2Properties;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 陈欢
 * @since 2019/11/21
 */
@EnableSwagger2
@Component
@EnableConfigurationProperties({Swagger2Properties.class})
public class SwaggerConfig {

  @Autowired
  private Swagger2Properties swagger2Properties;

  @Bean
  public Docket createRestApi() {
    List<Parameter> pars = new ArrayList<>();
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
    // return new Docket(DocumentationType.SWAGGER_2)
    //     .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(swagger2Properties.getInfo().getTitle())
        .description(swagger2Properties.getInfo().getDesc())
        .version(swagger2Properties.getInfo().getVersion())
        .build();
  }

}
