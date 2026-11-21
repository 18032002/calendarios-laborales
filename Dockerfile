# Etapa 1: Compilar el proyecto
FROM maven:3.8.5-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final ligera
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]