# Use OpenJDK 17 as base image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the correct JAR file from target folder into the container
COPY target/product-service-1.0-SNAPSHOT.jar product-service.jar

# Expose port 8081 for communication
EXPOSE 9090

# Set environment variables (optional)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-sims:3306/inventory
ENV SPRING_DATASOURCE_USERNAME=sims_user
ENV SPRING_DATASOURCE_PASSWORD=sims_password

# Start the application
CMD ["java", "-jar", "product-service.jar"]
