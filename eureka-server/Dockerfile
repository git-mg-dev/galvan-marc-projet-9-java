FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar medilabo-eureka-server.jar
ENTRYPOINT ["java", "-jar", "/medilabo-eureka-server.jar"]