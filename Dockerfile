FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app

COPY --from=build /app/model/target/*.jar model.jar
COPY --from=build /app/server/target/*.jar server.jar

EXPOSE 8080
CMD ["java", "-jar", "server.jar"]