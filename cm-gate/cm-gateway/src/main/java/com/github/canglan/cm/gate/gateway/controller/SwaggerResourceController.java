package com.github.canglan.cm.gate.gateway.controller;

import com.github.canglan.cm.gate.gateway.config.SwaggerResourceProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * Swagger-ui需要依赖接口
 *
 * @author bairitan
 * @date 2019/12/9
 */

@RestController
@RequestMapping("/swagger-resources")
public class SwaggerResourceController {

  private SwaggerResourceProvider swaggerResourceProvider;

  @Autowired
  public SwaggerResourceController(SwaggerResourceProvider swaggerResourceProvider) {
    this.swaggerResourceProvider = swaggerResourceProvider;
  }

  @RequestMapping(value = "/configuration/security")
  public ResponseEntity<SecurityConfiguration> securityConfiguration() {
    SecurityConfigurationBuilder builder = SecurityConfigurationBuilder.builder();
    // builder.clientId();
    return new ResponseEntity<>(builder.build(), HttpStatus.OK);
  }

  @RequestMapping(value = "/configuration/ui")
  public ResponseEntity<UiConfiguration> uiConfiguration() {
    return new ResponseEntity<>(UiConfigurationBuilder.builder().build(), HttpStatus.OK);
  }

  @RequestMapping
  public ResponseEntity<List<SwaggerResource>> swaggerResources() {
    return new ResponseEntity<>(swaggerResourceProvider.get(), HttpStatus.OK);
  }
}
