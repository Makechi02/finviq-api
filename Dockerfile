# Use an official OpenJDK runtime as the base image
FROM eclipse-temurin:21-jdk-alpine
LABEL authors="makechi"
VOLUME /tmp

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml ./
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the application code
COPY src ./src

# Package the Spring Boot app using Maven
RUN ./mvnw package -DskipTests

# Expose the port on which the Spring Boot app will run
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "target/ims-0.0.1-SNAPSHOT.jar"]
