# Use OpenJDK 17 as the base image with Alpine for a lightweight container
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Expose Eureka Server's default port
EXPOSE 8761

# Copy the built JAR file into the container from the local target folder
COPY target/eureka-server-1.0-SNAPSHOT.jar /app/eureka.jar

# Set the command to run the Eureka server JAR file
ENTRYPOINT ["java", "-jar", "/app/eureka.jar"]
