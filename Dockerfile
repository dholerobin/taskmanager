# Use the official OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/task-manager-0.0.1.jar /app/app.jar

# Specify the command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
