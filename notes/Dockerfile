FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar medilabo-notes-service.jar
ENTRYPOINT ["java", "-jar", "/medilabo-notes-service.jar"]