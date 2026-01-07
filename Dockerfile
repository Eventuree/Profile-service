FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S eventure && adduser -S eventure -G eventure

COPY target/*.jar app.jar

RUN chown eventure:eventure app.jar

USER eventure:eventure

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]