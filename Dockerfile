# Use a base image with Java and Maven pre-installed
FROM maven:3.8.6-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the project files to the working directory
COPY . .

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use a minimal image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/Api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
