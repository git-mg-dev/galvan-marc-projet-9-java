FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar medilabo-ui-service.jar
ENTRYPOINT ["java", "-jar", "/medilabo-ui-service.jar"]