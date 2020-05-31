### 鉴权服务 

​	选择使用 oauth2认证，支持所有的认证模式。

​	整合 spring-security-oauth 来进行权限认证；mysql 存储用户、客户端数据，redis缓存jwt、公钥、私钥、用户、客户端等信息。

1. ```ClientDetailService```管理client信息；

2. ```LocalUserDetailsService``` 管理 user 信息;
3.  token使用rsa加密来对token进行验证，并且spring-security 提供了获取rsa公钥接口，以便在client验证token。

 相关接口：

1. oauth/token_key（GET）

   获取公钥

2. oauth/check_token（POST）

   验证 token

3. oauth/token（POST）

   获取token与更新token

   

##### 授权类型

共有以下5种授权类型，grant_type : 验证类型（password,authorization_code,refresh_token,client_credentials,implicit）
    
   1. grant_type = password （密码模式）

        ```
        grant_type      : password
        scope           : 作用域
        client_id       : 客户端ID
        client_secret   : 客户端秘钥
        username        : 用户名 (gatnt_type=password)
        password        : 密码 (gatnt_type=password)
        ```

        

2. grant_type = authorization_code（授权码模式）

   ​	

   ```
   1. 首先获取授权码
   
      使用get方式访问 http://{HOST}:{PORT}/oauth/authorize?response_type=code&client_id=clientID&redirect_uri=http://baidu.com ，登录到授权服务器之后进行用户授权
      redirect_uri client_id 和 redirect_uri 必须定义。
   
      授权之后会跳转到 redirect_uri 指定的uri，并在后面追加一个 code。 
   
   2. 使用获取到的授权码(code)访问 http://{HOST}:{PORT}/oauth/token/oauth/token，获取token,参数如下
      grant_type      : authorization_code
      scope           : 作用域
      client_id       : 客户端ID
      client_secret   : 客户端秘钥
      code            : 授权码 (gatnt_type=authorization_code)
      redirect_uri    : 转发uri (gatnt_type=authorization_code)
   
   3. 通过获取到的token去访问资源
   ```

   

3. grant_type = implicit （隐形模式）

   

    直接访问下面URL获取token
    http://{HOST}:{PORT}/oauth/authorize?response_type=token&client_id=${clientId}&redirect_uri=${redirectUri} 


4. grant_type = implicit （客户端模式）

```
使用post 访问 http://{HOST}:{PORT}/oauth/token，并带以下参数，获取token
    grant_type      : client_credentials
    scope           : 作用域
    client_id       : 客户端ID
    client_secret   : 客户端秘钥
```



5. grant_type = refresh_token （刷新token）

    使用如下格式url进行刷新token
    http://{HOST}:{PORT}/oauth/token?grant_type=refresh_token&refresh_token=fbde81ee-f419-42b1-1234-9191f1f95be9&client_id=demoClientId&client_secret=demoClientSecret

