package com.github.canglan.cm.common.data.config;

import com.github.canglan.cm.common.data.properties.Swagger2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 陈欢
 * @since 2019/11/21
 */
@ConditionalOnProperty(value = "swagger2.enable", havingValue = "true", matchIfMissing = true)
@EnableSwagger2
@Component
@EnableConfigurationProperties({Swagger2Properties.class})
public class SwaggerConfig {

  public SwaggerConfig() {
    System.out.println("SwaggerConfig ============");
  }

  @Autowired
  private Swagger2Properties swagger2Properties;

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        // 不能使用以下两种方式，会获取不到接口，必须使用包名的方式进行
        // .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        // .apis(RequestHandlerSelectors.withClassAnnotation(Controller.class))
        .apis(RequestHandlerSelectors.basePackage(swagger2Properties.getInfo().getBasePackage()))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(swagger2Properties.getInfo().getTitle())
        .description(swagger2Properties.getInfo().getDesc())
        .version(swagger2Properties.getInfo().getVersion())
        .build();
  }

}
