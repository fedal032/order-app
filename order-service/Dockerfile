# Use the official OpenJDK 17 as a base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /order-service

# Copy the compiled JAR file into the container
COPY /target/order-service-0.0.1-SNAPSHOT.jar order-service.jar

# Expose the port that your Spring Boot application will run on
EXPOSE 8091

# Command to run your application
CMD ["java", "-jar", "order-service.jar"]