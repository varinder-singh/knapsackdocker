FROM openjdk:8-jdk-alpine
MAINTAINER maersk
EXPOSE 9090
COPY target/knapsackservice-1.0.0-SNAPSHOT.jar docker-knapsackservice-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-knapsackservice-1.0.0-SNAPSHOT.jar"]
