FROM openjdk:11.0.4-jre-slim

MAINTAINER Petro

ARG JAR_FILE
COPY "target/${JAR_FILE}" app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
