用于配置文件描述，基本上所有的配置文件都可以在这里面找到

```
spring-configuration-metadata.json
```



### 自定义相关配置

---

| 组       | 属性名                                              | 值：例                                  |
| -------- | --------------------------------------------------- | --------------------------------------- |
| 日志相关 | pure.log.dir                                        | 日志存储目录                            |
|          | pure.log.level                                      | 日志等级                                |
|          |                                                     |                                         |
| 应用相关 | pure.application.code                               | 系统code                                |
|          |                                                     |                                         |
|          |                                                     |                                         |
|          | pure.auth-server.jwt-token-signer.public-redis-key  | 存放到redis的加密oauth2 jwt rsa 公钥key |
|          | pure.auth-server.jwt-token-signer.private-redis-key | 存放到redis的加密oauth2 jwt rsa 私钥key |
|          |                                                     |                                         |



### 使用组将相关配置

---

| 组               | 属性名                                      | 值：例         |
| :--------------- | ------------------------------------------- | -------------- |
| nacos相关属性    | server.address.nacos.server-addr            | localhost:8848 |
|                  |                                             |                |
| sentinel相关配置 | server.address.sentinel.transport.port      | 20000          |
|                  | server.address.sentinel.transport.dashboard | localhost:8848 |
|                  |                                             |                |
| 数据库相关配置   | server.address.datasource.server-addr       | localhost:3306 |
|                  | server.address.datasource.username          |                |
|                  | server.address.datasource.password          |                |
|                  |                                             |                |
| redis相关配置    | server.address.redis.server-addr            | localhost      |
|                  | server.address.redis.port                   | 6379           |
|                  |                                             |                |
|                  |                                             |                |



