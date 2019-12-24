package com.github.canglan.cm.test.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.JacksonUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



@Slf4j
public class TestControllerTest {

  private static String gatewayUrl = "http://localhost:10001";

  private static RestTemplate restTemplate = new RestTemplate();

  static JacksonUtil jacksonUtil = JacksonUtil.singleInstance();

  public static void main(String[] args) throws IOException {
    Result loginResult = login();
    LoginEn loginEn = jacksonUtil.jsonToObject(jacksonUtil.toJson(loginResult.getData()), LoginEn.class);
    String accessToken = loginEn.getAccessToken();
    System.out.println(accessToken);

    String testParam = "经过网管调用服务";
    Result tokenReqTest = jwtCallService(accessToken, testParam);
    System.out.println(tokenReqTest);
    Assert.assertEquals(tokenReqTest.getData(), testParam);

    testParam = "经过网管服务调用服务";
    Result result = jwtServiceCallService(accessToken, testParam);
    System.out.println(result);
    Assert.assertEquals(result.getData(), testParam);

    System.out.println(getUserInfo(accessToken));
  }


  public static Result getUserInfo(String jwt) {
    MultiValueMap<String, String> objectObjectHashMap = new LinkedMultiValueMap<>();

    objectObjectHashMap.set("token", jwt);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Authorization", jwt);
    httpHeaders.set("contentType", "application/json;charset=UTF-8");

    org.springframework.http.HttpEntity<MultiValueMap<String, String>> httpEntity =
        new org.springframework.http.HttpEntity<>(objectObjectHashMap, httpHeaders);
    ResponseEntity<Result> resultResponseEntity =
        restTemplate.postForEntity(gatewayUrl + "/auth-server/authServer/getUser", httpEntity, Result.class);

    return resultResponseEntity.getBody();
  }

  public static Result jwtServiceCallService(String jwt, String testParam) {
    MultiValueMap<String, String> objectObjectHashMap = new LinkedMultiValueMap<>();
    objectObjectHashMap.add("testParam", testParam);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Authorization", jwt);
    httpHeaders.set("contentType", "application/json;charset=UTF-8");

    org.springframework.http.HttpEntity<MultiValueMap<String, String>> httpEntity =
        new org.springframework.http.HttpEntity<>(objectObjectHashMap, httpHeaders);
    ResponseEntity<Result> resultResponseEntity =
        restTemplate.postForEntity(gatewayUrl + "/test2/2/test/serviceCallService", httpEntity, Result.class);

    return resultResponseEntity.getBody();
  }

  /**
   * 请求测试，通过jwt经过网关调用服务
   */
  public static Result jwtCallService(String jwt, String testParam) throws IOException {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost httpPost = new HttpPost(gatewayUrl + "/test2/2/test/callService");
    httpPost.setHeader("Authorization", jwt);
    httpPost.setHeader("contentType", "application/json;charset=UTF-8");
    List<NameValuePair> paramList = new ArrayList<>();
    paramList.add(new BasicNameValuePair("testParam", testParam));
    httpPost.setEntity(new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8));
    CloseableHttpResponse execute = httpClient.execute(httpPost);
    HttpEntity entity = execute.getEntity();
    log.debug(" 响应信息 =  {}", entity);
    String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
    return JacksonUtil.singleInstance().jsonToObject(result, Result.class);
  }

  public static Result login() throws IOException {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost httpPost = new HttpPost(gatewayUrl + "/user/auth/login");
    List<NameValuePair> paramList = new ArrayList<>();
    paramList.add(new BasicNameValuePair("username", "admin"));
    paramList.add(new BasicNameValuePair("password", "admin"));
    httpPost.setEntity(new UrlEncodedFormEntity(paramList));
    CloseableHttpResponse execute = httpClient.execute(httpPost);
    HttpEntity entity = execute.getEntity();
    log.debug(" 响应信息 =  {}", entity);
    String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
    return JacksonUtil.singleInstance().jsonToObject(result, Result.class);
  }

}

@Data
class LoginEn {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("token_type")
  private String tokenType;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("expires_in")
  private String expiresIn;
  private String scope;
  private String jti;
  private String organization;
  private String oid;
  private String username;
  private List<String> roles;

}
