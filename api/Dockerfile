# Start with a base image containing Java runtime
FROM openjdk:13-jdk-slim

# Add Maintainer Info
LABEL maintainer="conradlparker@gmail.com"

COPY ./target/pet-api-0.0.1-SNAPSHOT.jar /usr/src/pet/

WORKDIR /usr/src/pet

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar", "pet-api-0.0.1-SNAPSHOT.jar"]
