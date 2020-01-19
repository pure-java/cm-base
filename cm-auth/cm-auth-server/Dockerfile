FROM bridg/java8

MAINTAINER chenhuan

EXPOSE 10002

VOLUME /cm-auth-server

COPY target/cm-auth-server.jar cm-auth-server.jar

RUN bash -c "touch /cm-auth-server.jar"

ENTRYPOINT ["java", "-jar","/cm-auth-server.jar"]