FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . .
ARG MODULE
RUN mvn -pl ${MODULE} -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
ARG MODULE
COPY --from=build /workspace/${MODULE}/target/*.jar app.jar
EXPOSE 8080 8081 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
