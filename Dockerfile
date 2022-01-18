# Base image
FROM openjdk:11

# runnable files
COPY target/*.jar app.jar

# how to run
CMD ["java", "-jar", "-Dspring.profiles.active=docker", "-Xmx300m", "-Xms300m","app.jar"]
