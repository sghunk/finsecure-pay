# Compile and Build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy configuration
COPY pom.xml .
COPY src ./src

# Compile production
RUN mvn clean package -DskipTests

# Lightweight Isolated App Execution Engine Runtime Context Environment
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Pull the compiled binary file from build scope container environment
COPY --from=build /app/target/*.jar app.jar

# server listener port mapping defaults
EXPOSE 8080

# Execute Spring Boot bootstrapper
ENTRYPOINT ["java", "-jar", "app.jar"]
