FROM eclipse-temurin:17-jammy
COPY build/libs/bowling-service.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]