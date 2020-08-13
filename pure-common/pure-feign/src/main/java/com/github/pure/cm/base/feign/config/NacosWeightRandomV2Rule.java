package com.github.pure.cm.base.feign.config;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.google.common.collect.Lists;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 * nacos权重 配合
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-08-12 10:56
 **/
@Slf4j
@Component
public class NacosWeightRandomV2Rule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }

    @Override
    public Server choose(Object key) {
        List<Server> servers = this.getLoadBalancer().getReachableServers();

        List<InstanceWithWeight> instanceWithWeights = servers.stream()
                .map(server -> {
                    // 注册中心只用Nacos，没同时用其他注册中心（例如Eureka），理论上不会实现
                    if (!(server instanceof NacosServer)) {
                        log.error("参数非法，server = {}", server);
                        throw new IllegalArgumentException("参数非法，不是NacosServer实例！");
                    }

                    NacosServer nacosServer = (NacosServer) server;
                    Instance instance = nacosServer.getInstance();
                    return new InstanceWithWeight(
                            server,
                            instance.getWeight()
                    );
                })
                .collect(Collectors.toList());

        return this.weightRandom(instanceWithWeights);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class InstanceWithWeight {
        private Server server;
        private double weight;
    }

    /**
     * 随机权重
     *
     * @param list 实例列表
     * @return 随机出来的结果
     */
    private Server weightRandom(List<InstanceWithWeight> list) {
        List<Server> instances = Lists.newArrayList();

        for (InstanceWithWeight instanceWithWeight : list) {
            double weight = instanceWithWeight.getWeight();
            for (int i = 0; i <= weight; i++) {
                instances.add(instanceWithWeight.getServer());
            }
        }
        // 随机打乱
        Collections.shuffle(instances);
        int i = new Random().nextInt(instances.size());
        return instances.get(i);
    }
}
