FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S eventure && adduser -S eventure -G eventure

COPY --from=builder /app/target/*.jar app.jar

USER eventure:eventure

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]