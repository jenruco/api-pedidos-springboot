# compilar
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

#montar compilado
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/target/pedidos-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]