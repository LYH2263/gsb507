# Build Stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY settings.xml .
COPY pom.xml .
# Download dependencies (cache layer)
RUN mvn -s settings.xml dependency:go-offline -B
COPY src ./src
RUN mvn -s settings.xml package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8507
ENTRYPOINT ["java", "-jar", "app.jar"]
