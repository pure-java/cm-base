package com.github.pure.cm.auth.server.service;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

/**
 * 自定义jdbc客户端详情
 *
 * @author bairitan
 * @date 2019/12/4
 */
@CacheConfig(cacheNames = "clientDetailService")
public class ClientDetailService extends JdbcClientDetailsService {

  public ClientDetailService(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  @Cacheable(value = {"loadClientByClientId"}, key = "#p0")
  public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
    return super.loadClientByClientId(clientId);
  }

  @Override
  public List<ClientDetails> listClientDetails() {
    return super.listClientDetails();
  }

  @Override
  public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
    super.addClientDetails(clientDetails);
  }

  @Override
  @CacheEvict(value = {"loadClientByClientId"}, key = "#p0.clientId")
  public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
    super.updateClientDetails(clientDetails);
  }

  @Override
  @CacheEvict(value = {"loadClientByClientId"}, key = "#p0")
  public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
    super.updateClientSecret(clientId, secret);
  }

  @Override
  @CacheEvict(value = {"loadClientByClientId"}, key = "#p0")
  public void removeClientDetails(String clientId) throws NoSuchClientException {
    super.removeClientDetails(clientId);
  }
}
