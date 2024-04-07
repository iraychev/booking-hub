FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy the entire project to the Docker image
COPY . .

# Build the Maven project (including both 'model' and 'server' subdirectories)
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app

# Copy the built JAR files from the previous stage
COPY --from=build /app/model/target/*.jar model.jar
COPY --from=build /app/server/target/*.jar server.jar

EXPOSE 8080
CMD ["java", "-jar", "server.jar"]