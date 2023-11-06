# Start with a base image containing Java runtime
FROM eclipse-temurin:17-jdk

# Add Maintainer Info
MAINTAINER wink


COPY target/weatherapi-0.0.1-SNAPSHOT.jar /app/


EXPOSE 80 25060

# Run the jar file

ENTRYPOINT ["java","-jar", "app/weatherapi-0.0.1-SNAPSHOT.jar"]

