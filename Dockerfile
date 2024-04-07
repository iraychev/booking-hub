FROM maven:3.8.4-openjdk-17-slim AS model-build
WORKDIR /app/model
COPY model/pom.xml .
RUN mvn dependency:go-offline
COPY model/src ./src
RUN mvn package -DskipTests

FROM maven:3.8.4-openjdk-17-slim AS server-build
WORKDIR /app/server
COPY server/pom.xml .
RUN mvn dependency:go-offline
COPY server/src ./src
RUN mvn package -DskipTests


FROM openjdk:17-slim
WORKDIR /app
COPY --from=model-build /app/model/target/*.jar model.jar
COPY --from=server-build /app/server/target/*.jar server.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
