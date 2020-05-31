package com.github.pure.cm.auth.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author bairitan
 * @since 2019/11/18
 */
@Configuration
@EnableFeignClients({"com.github.pure.cm.auth.client.feign"})
public class AuthClientApplication {

}
