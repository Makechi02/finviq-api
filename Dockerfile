# Use an official OpenJDK runtime as the base image
FROM eclipse-temurin:22-jdk-alpine
LABEL authors="makechi"
VOLUME /tmp

# Set the working directory in the container
WORKDIR /app

# Copy Maven Wrapper and POM files
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

# Make Maven Wrapper executable
RUN chmod +x mvnw

# Download Maven dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the application code
COPY src ./src

# Package the Spring Boot app using Maven Wrapper
RUN ./mvnw package -DskipTests

# Expose the port on which the Spring Boot app will run
EXPOSE 8080

# Set the environment variable for the profile
ENV SPRING_PROFILES_ACTIVE=prod

# Command to run the Spring Boot application
CMD ["java", "-jar", "target/finviq-0.0.1-SNAPSHOT.jar"]
