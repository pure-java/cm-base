###用于学习使用
  
 该项目主要用于学习。
 
 spring-cloud-me 基于spring cloud进行开发，具有统一权限认证、用户管理、网关API等功能。
 
 * 服务发现/服务注册中心：nacos  
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

 授权服务使用 mysql +  mybatis 存储 user 信息， ```ClientDetailService``` 将 client 信息存储在mysql中。
    
 使用 rsa 加密 token，spring-security 提供了获取rsa公钥接口，以便在client验证token。
 
 redis 存储 token、refresh token、private rsa、public rsa 等信息。
 
 验证相关接口：
 
     /oauth/token：获取 token以及刷新 token
     
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
