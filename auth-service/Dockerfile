# Use OpenJDK 17 with Alpine as a base image
FROM openjdk:17-jdk-alpine

# Set the volume for temporary files
VOLUME /tmp

# Expose the application port (default port for Spring Boot is 8080)
EXPOSE 8080

# Copy the built JAR file from the target folder into the container
COPY target/auth-service-1.0-SNAPSHOT.jar /auth.jar

# Set the command to run the auth-service JAR file
ENTRYPOINT ["java", "-jar", "/auth.jar"]
