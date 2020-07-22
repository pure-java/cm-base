package com.github.pure.cm.gate.gateway.config.router;

import com.github.pure.cm.common.core.util.JsonUtil;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 数据库存储 路由
 *
 * @author bairitan
 * @date 2019/12/5
 */
@Slf4j
@Component
public class RedisRouterRouteDefinitionRepository implements RouteDefinitionRepository {

  public static final String GATEWAY_ROUTES = "gateway_routes::";

  @Autowired
  private StringRedisTemplate redisTemplate;
  private final JsonUtil jsonUtil = JsonUtil.singleInstance();
  private Map<String, RouteDefinition> routeDefinitionMap = new ConcurrentHashMap<>();

  private void loadRouterDefinition() {
    Set<String> keys = redisTemplate.keys(GATEWAY_ROUTES + "*");
    if (CollectionUtils.isNotEmpty(keys)) {
      List<String> list = redisTemplate.opsForValue().multiGet(keys);
      log.debug("load redis router = {} ", list);
      if (CollectionUtils.isNotEmpty(list)) {
        list.forEach(router -> {
          RouteDefinition routeDefinition = jsonUtil.jsonToObject(router, RouteDefinition.class);
          routeDefinitionMap.put(routeDefinition.getId(), routeDefinition);
          log.debug(" gateway router {} = {}", routeDefinition.getId(), routeDefinition);
        });
      }
    }
  }

  @Override
  public Flux<RouteDefinition> getRouteDefinitions() {
    this.loadRouterDefinition();
    return Flux.fromIterable(routeDefinitionMap.values());
  }

  @Override
  public Mono<Void> save(Mono<RouteDefinition> route) {
    return route.flatMap(routeDefinition -> {
          routeDefinitionMap.put(routeDefinition.getId(), routeDefinition);
          String value = jsonUtil.toJson(routeDefinition);
          if (value != null) {
            redisTemplate.opsForValue().set("GATEWAY_ROUTES" + routeDefinition.getId(), value);
          }
          return Mono.empty();
        }
    );
  }

  @Override
  public Mono<Void> delete(Mono<String> routeId) {
    return routeId.flatMap(id -> {
      routeDefinitionMap.remove(id);
      redisTemplate.delete("GATEWAY_ROUTES" + id);
      return Mono.empty();
    });
  }

}
