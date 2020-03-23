# 使用jdk8做为基础镜像
FROM bridg/java8
MAINTAINER chenhuan

#暴露容器端口
EXPOSE 10001 20001

VOLUME /cm-gateway
#将复制指定的jar到容器中，并且指定名称，相当于拷贝到容器中取了个别名
COPY target/cm-gateway.jar cm-gateway.jar

#创建一个新的容器并在新的容器中运行命令
RUN bash -c 'touch /cm-gateway.jar'
#相当于在容器中用cmd命令执行jar包
ENTRYPOINT ["java","-jar","/cm-gateway.jar"]
