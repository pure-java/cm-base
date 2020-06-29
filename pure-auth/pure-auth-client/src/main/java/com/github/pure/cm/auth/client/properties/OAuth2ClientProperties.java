package com.github.pure.cm.auth.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * oauth2 客户端属性
 *
 * @author bairitan
 * @date 2019/12/9
 */
@Data
@ConfigurationProperties(prefix = "security.oauth2.client")
@Component
public class OAuth2ClientProperties {

  /**
   * 登录类型
   * <br>
   * grant_type
   */
  private String grantType;
  /**
   * 客户端ID或者名称
   * <br>
   * client_id
   */
  private String clientId;
  /**
   * 客户端 授权码（客户端密码）
   * <br>
   * client_secret
   */
  private String clientSecret;

  /**
   * 资源ID s
   */
  private String resourceIds;
  /**
   * 客户端作用域
   */
  private String scope;
  /**
   * 权限认证类型 ,支持的 grant_type
   * <br>
   * authorized_grant_types
   */
  private String authorizedGrantTypes;
  /**
   * 重定向 uri， 当grant_type为authorization_code或implicit时, 在Oauth的流程中会使用并检查与注册时填写的redirect_uri是否一致.
   * <br>
   * web_server_redirect_uri
   */
  private String webServerRedirectUri;
  /**
   * 指定客户端所拥有的Spring Security的权限值,
   */
  private String authorities;
  /**
   * 用户授权url
   */
  private String userAuthorizationUri;
  /**
   * 认证 uri
   */
  private String accessTokenUri;
  /**
   * 获取用户信息uri
   */
  private String userInfoUri;
  /**
   * 检查token uri
   */
  private String checkTokenAccess;
}
