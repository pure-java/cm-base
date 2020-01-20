### 用于学习使用
  
 主要用于学习。
 
 spring-cloud-me 基于spring cloud进行开发，具有统一权限认证、用户管理、网关API等功能。
 
 * 服务发现/服务注册中心：nacos  
 * 微服务监控：spring cloud admin
 * 配置中心：nacos  
 * 网关：spring-cloud-gateway  
 * 权限验证：spring-security  
 * 熔断/流量卫兵：sentinel
 * 链路跟踪：skywalking （项目未集成，生产环境部署时用 skywalking 启动项目）
 * 数据库：mysql
 * JDK：Oracle java 8
 * NOSQL：redis
 
 ---
     需要注意 spring boot 与 spring cloud 之间需要注意版本依赖关系。
     spring-boot-starter-parent          2.1.9.RELEASE
     spring-cloud-dependencies           Greenwich.SR3
     spring-cloud-alibaba-dependencies   2.1.1.RELEASE

#### 授权服务 cm-auth-server

 使用 spring-security-oauth、mysql 来开发授权服务，支持各种验证模式。user信息与client信息存储在mysql中；使用 redis 缓存 
 token、refresh token、private rsa、public rsa 、user、client等信息。
 
 ```ClientDetailService```管理client信息，```LocalUserDetailsService``` 管理 user 信息。token使用rsa加密，
 并且spring-security 提供了获取rsa公钥接口，以便在client验证token。
 
 相关接口：
 
     /oauth/token：获取 token以及刷新 token
        grant_type      : 验证类型（password,authorization_code,refresh_token,client_credentials,implicit）
        
       1. grant_type = password （密码模式）
            scope           : 作用域
            client_id       : 客户端ID
            client_secret   : 客户端秘钥
            username        : 用户名 (gatnt_type=password)
            password        : 密码 (gatnt_type=password)
       2. grant_type = authorization_code（授权码模式）
            1. 首先获取授权码
             
                使用get方式访问 http://{HOST}:{PORT}/oauth/authorize?response_type=code&client_id=clientID&redirect_uri=http://baidu.com ，登录到授权服务器之后进行用户授权
                redirect_uri client_id 和 redirect_uri 必须定义。
                 
                授权之后会跳转到 redirect_uri 指定的uri，并在后面追加一个 code。 
               
            2. 使用获取到的授权码(code)访问 http://{HOST}:{PORT}/oauth/token/oauth/token，获取token
                grant_type      : authorization_code
                scope           : 作用域
                client_id       : 客户端ID
                client_secret   : 客户端秘钥
                code            : 授权码 (gatnt_type=authorization_code)
                redirect_uri    : 转发uri (gatnt_type=authorization_code)
            3. 通过获取到的token去访问资源
       3. grant_type = implicit （隐形模式）
       
        直接访问 http://{HOST}:{PORT}/oauth/authorize?response_type=token&client_id=test&redirect_uri=http://baidu.com 获取token
       4. grant_type = implicit （客户端模式）
            使用post 访问 http://{HOST}:{PORT}/oauth/token，并带一下参数，获取token
                grant_type      : client_credentials
                scope           : 作用域
                client_id       : 客户端ID
                client_secret   : 客户端秘钥
       5. grant_type = refresh_token （刷新token）
       
        访问 http://{HOST}:{PORT}/oauth/token?grant_type=refresh_token&refresh_token=fbde81ee-f419-42b1-1234-9191f1f95be9&client_id=demoClientId&client_secret=demoClientSecret
     /oauth/check_token：验证 token
     
     /oauth/token_key：获取rsa公钥
  
#### 授权服务客户端 cm-auth-client
 
 提供快捷访问授权验证服务 AuthService。
 
#### 网关服务 cm-gateway

网关服务使用 spring-cloud-gateway，并且使用 sentinel、nacos、spring-security、redis。

用户首先通过网关提供的登录接口获取token，然后通过有效token访问资源。

在访问资源时，经过 JwtTokenFilter 验证是否授权以及验证 token 是否有效，如果没有授权码或者token无效，则不能访问资源。

####部署
   
   如果使用docker部署，需要设置为 --network host 模式，以便 nacos 能提供宿主机的IP地址，以便其他服务正确访问。 
