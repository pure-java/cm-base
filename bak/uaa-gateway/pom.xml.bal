<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!--
        <parent>
            <groupId>com.github.pure</groupId>
            <artifactId>pure-dependencies</artifactId>
            <version>1.0-SNAPSHOT</version>
        </parent>
        <modelVersion>4.0.0</modelVersion>

        <artifactId>uaa-gateway</artifactId>
        <dependencies>
            <dependency>
                <groupId>com.github.pure</groupId>
                <artifactId>pure-common-core</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>

            <dependency>
                <groupId>com.github.pure</groupId>
                <artifactId>uaa-resource</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>

            <dependency>
                <groupId>com.github.pure</groupId>
                <artifactId>pure-feign</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>

            <dependency>
                <groupId>org.springframework.security</groupId>
                <artifactId>spring-security-jwt</artifactId>
            </dependency>

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-gateway</artifactId>
            </dependency>

            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
            </dependency>

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-openfeign</artifactId>
                <exclusions>
                    <exclusion>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-web</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>


            &lt;!&ndash;ali服务注册中心与配置中心&ndash;&gt;
            &lt;!&ndash; https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-discovery &ndash;&gt;
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            </dependency>

            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            </dependency>

            <dependency>
                <groupId>com.alibaba.csp</groupId>
                <artifactId>sentinel-datasource-nacos</artifactId>
            </dependency>

            <dependency>
                <groupId>com.alibaba.csp</groupId>
                <artifactId>sentinel-transport-simple-http</artifactId>
            </dependency>
            <dependency>
                <groupId>com.github.pure</groupId>
                <artifactId>pure-redis</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>

            &lt;!&ndash;RESTful API文档&ndash;&gt;
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-swagger2</artifactId>
            </dependency>

            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-swagger-ui</artifactId>
            </dependency>

        </dependencies>

        <build>
            <finalName>${project.name}</finalName>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <configuration>
                        <fork>true</fork>
                        <mainClass>com.github.pure.cm.gate.gateway.GatewayApplication</mainClass>
                    </configuration>
                </plugin>

                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-resources-plugin</artifactId>
                    <configuration>
                        <delimiters>
                            <delimiter>@</delimiter>
                        </delimiters>
                        <useDefaultDelimiters>false</useDefaultDelimiters>
                    </configuration>
                </plugin>
            </plugins>


            <resources>
                <resource>
                    <directory>src/main/resources</directory>
                    <includes>
                        <include>**/*</include>
                    </includes>
                    <filtering>true</filtering>
                </resource>
            </resources>
        </build>-->
</project>

