package com.github.pure.cm.common.core.callback;


import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.github.pure.cm.common.core.model.Result;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author bairitan
 * @date 2020/1/8
 */
public class CustomBlockRequestHandler implements BlockRequestHandler {

  private static final String DEFAULT_BLOCK_MSG_PREFIX = "Blocked by Sentinel: ";

  @Override
  public Mono<ServerResponse> handleRequest(
      ServerWebExchange exchange, Throwable ex) {
    if (acceptsHtml(exchange)) {
      return this.htmlErrorResponse(ex);
    }
    return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(BodyInserters.fromObject(buildErrorResult(ex)));
  }

  private Result<String> buildErrorResult(Throwable ex) {
    final Result<String> fail = Result.fail(DEFAULT_BLOCK_MSG_PREFIX + ex.getClass().getSimpleName());
    fail.setCode(HttpStatus.TOO_MANY_REQUESTS.value());
    return fail;
  }

  private boolean acceptsHtml(ServerWebExchange exchange) {
    try {
      List<MediaType> acceptedMediaTypes = exchange.getRequest().getHeaders().getAccept();
      acceptedMediaTypes.remove(MediaType.ALL);
      MediaType.sortBySpecificityAndQuality(acceptedMediaTypes);
      return acceptedMediaTypes.stream()
          .anyMatch(MediaType.TEXT_HTML::isCompatibleWith);
    } catch (InvalidMediaTypeException ex) {
      return false;
    }
  }

  private Mono<ServerResponse> htmlErrorResponse(Throwable ex) {
    return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
        .contentType(MediaType.TEXT_PLAIN)
        .syncBody(DEFAULT_BLOCK_MSG_PREFIX + ex.getClass().getSimpleName());
  }

}
