FROM maven:3.9.6-eclipse-temurin-17-alpine as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /usr/app/target/risk-0.0.1-SNAPSHOT.jar /app/risk.jar
EXPOSE 9003
ENTRYPOINT java -jar /app/risk.jar