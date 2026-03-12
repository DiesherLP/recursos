# Etapa 1: Compilar el proyecto
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecutar la aplicación
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 10000
CMD ["java", "-Xmx256m", "-jar", "app.jar"]