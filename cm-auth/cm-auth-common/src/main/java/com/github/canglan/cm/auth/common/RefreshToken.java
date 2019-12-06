package com.github.canglan.cm.auth.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author bairitan
 * @date 2019/12/6
 */
@Data
public class RefreshToken {

  @JsonProperty("grant_type")
  private String grantType;
  @JsonProperty("client_id")
  private String clientId;
  @JsonProperty("client_secret")
  private String clientSecret;
  @JsonProperty("refresh_token")
  private String refreshToken;
  
}
