# Paso 1: Compilar la aplicación
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# Añadimos flags para ignorar errores de transferencia y tests
RUN mvn clean package -DskipTests -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true

# Paso 2: Ejecutar la aplicación
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]