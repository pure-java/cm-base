FROM bridg/java8

MAINTAINER chenhuan

EXPOSE 10002
EXPOSE 20002

VOLUME /pure-auth-server

COPY target/pure-auth-server.jar pure-auth-server.jar

RUN bash -c "touch /pure-auth-server.jar"

ENTRYPOINT ["java", "-jar","/pure-auth-server.jar"]