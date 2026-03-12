# Paso 1: Compilar la aplicación
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# Cambiamos ./mvnw por mvn directo para evitar el error del wrapper
RUN mvn clean package -DskipTests

# Paso 2: Ejecutar la aplicación
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]