# Use an official OpenJDK runtime as the base image
FROM eclipse-temurin:17-jdk-jammy as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Copy the Maven wrapper (if you're using it)
COPY mvnw .
COPY .mvn .mvn

# Build the application using Maven
RUN ./mvnw clean package -DskipTests

# Use a smaller base image for the final runtime
FROM eclipse-temurin:17-jre-jammy

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder target/eureka-server-1.0-SNAPSHOT.jar eurekaserver.jar

# Expose the Eureka Server port
EXPOSE 8761

# Command to run the application
ENTRYPOINT ["java", "-jar", "eurekaserver.jar"]