FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar medilabo-auth-service.jar
ENTRYPOINT ["java", "-jar", "/medilabo-auth-service.jar"]