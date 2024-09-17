FROM eclipse-temurin:21-jdk-alpine
LABEL authors="makechi"
VOLUME /tmp

COPY target/*.jar /app/app.jar

WORKDIR /app

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Expose the port on which the Spring Boot app will run
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "target/ims-0.0.1-SNAPSHOT.jar"]
