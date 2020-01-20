FROM bridg/java8

MAINTAINER chenhuan

EXPOSE 8769

VOLUME /cm-admin-server

COPY target/cm-admin-server.jar cm-admin-server.jar

RUN bash -c "touch /cm-admin-server.jar"
ENTRYPOINT ["java","-jar","/cm-admin-server.jar"]