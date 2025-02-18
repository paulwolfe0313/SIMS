# Use OpenJDK 17 as the base image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/notification-service-1.0-SNAPSHOT.jar notification-service.jar

# Expose port (choose a new port for this service)
EXPOSE 9092

# Command to run the application
CMD ["java", "-jar", "notification-service.jar"]
