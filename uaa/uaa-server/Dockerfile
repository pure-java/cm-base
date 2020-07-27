FROM bridg/java8

MAINTAINER chenhuan

EXPOSE 10002
EXPOSE 20002

VOLUME /uaa-server

COPY target/uaa-server.jar uaa-server.jar

RUN bash -c "touch /uaa-server.jar"

ENTRYPOINT ["java", "-jar","/uaa-server.jar"]
