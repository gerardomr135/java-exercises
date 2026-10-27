# ==============================
# Stage 1: Build the Application
# ==============================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy the pom.xml file first to cache dependencies
COPY pom.xml .

# Download dependencies. This layer will be cached unless pom.xml changes
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application (skipping tests to speed up the container build)
RUN mvn clean package -DskipTests

# ==============================
# Stage 2: Run the Application
# ==============================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy the built JAR file from the 'build' stage
# Note: The name is derived from artifactId + version in your pom.xml
COPY --from=build /app/target/data-formats-shop-1.0-SNAPSHOT.jar app.jar
COPY input /app/input

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]