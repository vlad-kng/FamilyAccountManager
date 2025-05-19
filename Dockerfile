# Сборка приложения
FROM gradle:8.5-jdk21 AS builder
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle bootJar --no-daemon

# Запуск приложения
FROM openjdk:21-slim
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]