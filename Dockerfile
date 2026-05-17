# CI test-and-build job'ında üretilen bootJar ile kullanılır (context'e app.jar olarak kopyalanır).
FROM eclipse-temurin:21-jre-alpine

ARG GIT_COMMIT=unknown
LABEL org.opencontainers.image.revision="${GIT_COMMIT}"

WORKDIR /app
COPY app.jar app.jar

EXPOSE 8080
USER 1000:1000
ENTRYPOINT ["java", "-jar", "app.jar"]