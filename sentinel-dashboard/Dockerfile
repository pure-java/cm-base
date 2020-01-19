# 使用jdk8做为基础镜像
FROM bridg/java8
MAINTAINER chenhuan

#暴露容器端口
EXPOSE 20000

VOLUME /sentinel-dashboard
#将复制指定的jar到容器中，并且指定名称，相当于拷贝到容器中取了个别名
COPY target/sentinel-dashboard.jar sentinel-dashboard.jar
#创建一个新的容器并在新的容器中运行命令
RUN bash -c 'touch /sentinel-dashboard.jar'
#启动容器就执行该命令
ENTRYPOINT ["java","-jar","/sentinel-dashboard.jar"]
