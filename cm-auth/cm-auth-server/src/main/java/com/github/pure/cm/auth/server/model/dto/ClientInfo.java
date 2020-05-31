package com.github.pure.cm.auth.server.model.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

/**
 * 客户端信息
 *
 * @author 陈欢
 * @see org.springframework.security.oauth2.provider.ClientDetails
 * @since 2019/12/1
 */
@Data
@Accessors(chain = true)
public class ClientInfo {

  private String clientId;

  private String clientSecret;

  private Set<String> scope = Collections.emptySet();

  private Set<String> resourceIds = Collections.emptySet();

  private Set<String> authorizedGrantTypes = Collections.emptySet();

  private Set<String> registeredRedirectUri;

  private Set<String> autoApproveScopes;

  private List<GrantedAuthority> authorities = Collections.emptyList();

  private Integer accessTokenValiditySeconds;

  private Integer refreshTokenValiditySeconds;

  private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
}
