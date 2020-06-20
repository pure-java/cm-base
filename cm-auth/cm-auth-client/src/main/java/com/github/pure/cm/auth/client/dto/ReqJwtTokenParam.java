package com.github.pure.cm.auth.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pure.cm.common.core.util.JsonUtil;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 关于 oauth2  jwt token 相关请求参数
 *
 * @author bairitan
 * @date 2019/12/6
 */
@Data
@Accessors(chain = true)
public class ReqJwtTokenParam {

  /**
   * 请求类型，authorization_code,password,refresh_token,implicit,client_credentials
   */
  @JsonProperty("grant_type")
  private String grantType;
  /**
   * 作用域
   */
  @JsonProperty("scope")
  private String scope;
  /**
   * 客户端id，不能为空
   */
  @JsonProperty("client_id")
  private String clientId;
  /**
   * 客户端访问密匙，不能为空
   */
  @JsonProperty("client_secret")
  private String clientSecret;
  /**
   * 刷新token，在 grantType = refresh_token 时使用
   */
  @JsonProperty("refresh_token")
  private String refreshToken;
  /**
   * 用户名，在 grantType = password 时使用
   */
  @JsonProperty("username")
  private String username;
  /**
   * 密码，在 grantType = password 时使用
   */
  @JsonProperty("password")
  private String password;

  public Map<String, Object> toMap() {
    return JsonUtil.singleInstance().jsonToMap(JsonUtil.json(this));
  }
}
