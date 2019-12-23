package com.github.canglan.cm.gate.gateway.filter;

import com.github.canglan.cm.auth.client.feign.AuthProvider;
import com.github.canglan.cm.auth.client.service.AuthService;
import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.JacksonUtil;
import com.github.canglan.cm.gate.gateway.properties.AuthProperties;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.jwt.Jwt;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author bairitan
 * @date 2019/12/4
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class JwtTokenFilter implements GlobalFilter {

  private AuthService authService;
  private AuthProvider authValidateService;
  private AuthProperties authProperties;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    String method = request.getMethodValue();
    String url = request.getURI().getPath();
    log.debug(" exchange.getRequest().getURI().getPath() = {} ", request.getURI().getPath());
    log.debug(" exchange.getRequest().getPath() = {}", request.getPath());
    if (StringUtils.isNotBlank(url) && authProperties.ignoreAuthentication(url)) {
      return chain.filter(exchange);
    }
    if (StringUtils.isNotBlank(authentication)) {

      Jwt jwt = authService.decodeAndVerify(authentication);
      log.debug("jwt = {}", jwt);
      Map<String, Object> checkToken = authService.checkToken(authentication);

      if (Objects.nonNull(checkToken)) {
        ServerHttpRequest.Builder builder = request.mutate();
        return chain.filter(exchange.mutate().request(builder.build()).build());
        //如果是get请求，放行
        // if (method.equals(HttpMethod.GET.toString())) {
        // builder.header(SecurityConstants.AUTH_HEADER, userJson);
        // return chain.filter(exchange.mutate().request(builder.build()).build());
        // } else {
        //如果有权限，放行
        // if (havePermission(btns, url)) {
        //   builder.header(SecurityConstants.AUTH_HEADER, userJson);
        // }
        // return chain.filter(exchange.mutate().request(builder.build()).build());
        // return writeResponse(exchange, "没有操作权限", HttpStatus.OK);
        // }
      }
    } else {
      exchange.getResponse().setStatusCode(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.getReasonPhrase().getBytes());
      return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    return null;
  }

  private Mono<Void> writeResponse(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
    ServerHttpResponse serverHttpResponse = exchange.getResponse();
    serverHttpResponse.setStatusCode(httpStatus);
    Result result = new Result();
    result.setCode(httpStatus.value());
    result.setMessage(message);
    byte[] bytes = JacksonUtil.json(result).getBytes(StandardCharsets.UTF_8);
    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
    return exchange.getResponse().writeWith(Flux.just(buffer));
  }

}
