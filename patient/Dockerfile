FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar medilabo-patient-service.jar
ENTRYPOINT ["java", "-jar", "/medilabo-patient-service.jar"]