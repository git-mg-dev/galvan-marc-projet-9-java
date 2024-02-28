FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar medilabo-gateway-service.jar
ENTRYPOINT ["java", "-jar", "/-medilabo-gateway-service.jar"]