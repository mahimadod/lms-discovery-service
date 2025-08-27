# Use OpenJDK base image
FROM openjdk:17-jdk-alpine

FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
