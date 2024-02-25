# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the compiled application from the build environment to the container
COPY ./target/y_chits.jar ./y_chits.jar

# Expose port 8080 to the Docker host
EXPOSE 1001

# Run the application when the container starts
CMD ["java", "-jar", "y_chits.jar"]