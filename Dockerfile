FROM maven:3.9.8-amazoncorretto-21 AS build
WORKDIR /app
COPY target/meupet-0.0.1-SNAPSHOT.jar /app/meupet.jar
ENV PORT=8080
ENV SPRING_DATASOURCE_URL={SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME={SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD={SPRING_DATASOURCE_PASSWORD}

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app

EXPOSE 8080
COPY --from=build /app/target/meupet-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]