# Stage 1: Build the application
FROM openjdk:17-jdk-alpine as builder

WORKDIR /app

# Copy Maven configuration and dependencies
COPY ./pom.xml ./
COPY ./.mvn ./.mvn
COPY ./mvnw ./

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY ./src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/api-paths-0.0.1-SNAPSHOT.jar ./api-paths.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "api-paths.jar"]