# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /ProductServiceAPI

# Copy the built JAR file from the target directory in the host to the container's working directory
COPY target/ProductServiceAPI-0.0.1.jar ProductServiceAPI-0.0.1.jar

# Expose the port your application will run on
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "ProductServiceAPI-0.0.1.jar"]
