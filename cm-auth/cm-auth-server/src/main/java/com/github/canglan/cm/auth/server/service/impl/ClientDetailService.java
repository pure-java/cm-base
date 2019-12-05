package com.github.canglan.cm.auth.server.service.impl;

import java.util.List;
import javax.sql.DataSource;
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
public class ClientDetailService extends JdbcClientDetailsService {

  public ClientDetailService(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
    return super.loadClientByClientId(clientId);
  }

  @Override
  public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
    super.addClientDetails(clientDetails);
  }

  @Override
  public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
    super.updateClientDetails(clientDetails);
  }

  @Override
  public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
    super.updateClientSecret(clientId, secret);
  }

  @Override
  public void removeClientDetails(String clientId) throws NoSuchClientException {
    super.removeClientDetails(clientId);
  }

  @Override
  public List<ClientDetails> listClientDetails() {
    return super.listClientDetails();
  }
}
