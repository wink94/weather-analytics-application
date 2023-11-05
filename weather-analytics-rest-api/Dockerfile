# Start with a base image containing Java runtime
FROM eclipse-temurin:17-jdk as builder

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar

# Unpack the jar file
RUN java -Djarmode=layertools -jar app.jar extract

# Start with a new base image for the final build
FROM eclipse-temurin:17-jdk

# Copy the unpacked application files
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

