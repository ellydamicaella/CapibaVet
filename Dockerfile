# Etapa de build com Maven e Amazon Corretto JDK 21
FROM maven:3.9.8-amazoncorretto-21 AS build
WORKDIR /app

# Copiar o código fonte para o diretório /app
COPY . .

# Construir o projeto, pulando os testes
RUN mvn clean package -DskipTests

# Etapa final com OpenJDK 21
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copiar o arquivo .jar do estágio de build para o contêiner final
COPY --from=build /app/target/meupet-0.0.1-SNAPSHOT.jar app.jar

# Expôr a porta 8080, que é onde o seu app será executado
EXPOSE 8080

# Definir o comando de execução do container
ENTRYPOINT ["java", "-jar", "app.jar"]