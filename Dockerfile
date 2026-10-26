# Phase 1: Compile and Build Environment Container
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy configuration and configurations dependencies metadata
COPY pom.xml .
COPY src ./src

# Compile production-ready distribution package, skipping active test checks for speed
RUN mvn clean package -DskipTests

# Phase 2: Lightweight Isolated App Execution Engine Runtime Context Environment
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Pull the compiled binary file from Phase 1 build scope container environment
COPY --from=build /app/target/*.jar app.jar

# Expose server listener port mapping defaults
EXPOSE 8080

# Execute Spring Boot bootstrapper instruction sets
ENTRYPOINT ["java", "-jar", "app.jar"]
