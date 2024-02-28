FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar medilabo-risk-service.jar
ENTRYPOINT ["java", "-jar", "/medilabo-risk-service.jar"]