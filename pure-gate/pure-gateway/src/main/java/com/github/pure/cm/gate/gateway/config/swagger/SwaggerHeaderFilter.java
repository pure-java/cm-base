package com.github.pure.cm.gate.gateway.config.swagger;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * @author bairitan
 * @date 2019/12/9
 */
// @Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {

  private static final String HEADER_NAME = "X-Forwarded-Prefix";

  private static final String HOST_NAME = "X-Forwarded-Host";

  @Override
  public GatewayFilter apply(Object config) {
    // return (exchange, chain) -> {
    //   ServerHttpRequest request = exchange.getRequest();
    //   String path = request.getURI().getPath();
    //   if (!StringUtils.endsWithIgnoreCase(path, SwaggerProvider.API_URI)) {
    //     return chain.filter(exchange);
    //   }
    //   String basePath = path.substring(0, path.lastIndexOf(SwaggerProvider.API_URI));
    //   ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
    //   ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
    //   return chain.filter(newExchange);

    // };
    return null;
  }
}