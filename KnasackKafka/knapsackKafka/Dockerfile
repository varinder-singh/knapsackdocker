FROM openjdk:8-jdk-alpine
MAINTAINER maersk
EXPOSE 9091
COPY target/knapsackkafkaservice-1.0.0-SNAPSHOT.jar docker-knapsackkafkaservice-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-knapsackkafkaservice-1.0.0-SNAPSHOT.jar"]