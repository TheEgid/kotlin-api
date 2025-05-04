# ===== Build Stage =====
FROM gradle:8.5.0-jdk17-alpine AS build

WORKDIR /build

COPY app /build/app

WORKDIR /build/app

# Кэширование зависимостей
RUN gradle dependencies --configuration runtimeClasspath --no-daemon

# Сборка fat jar (нужно добавить shadowJar в build.gradle.kts)
RUN gradle shadowJar --no-daemon

# ===== Runtime Stage =====
FROM eclipse-temurin:17-jre-alpine

RUN apk add --no-cache curl
RUN addgroup -S appuser && adduser -S appuser -G appuser
USER appuser

WORKDIR /home/appuser/app

# Путь должен совпадать с местом где Gradle собирает fat JAR
COPY --from=build --chown=appuser:appuser /build/app/build/libs/app-all.jar app.jar

HEALTHCHECK --interval=30s --timeout=3s CMD curl --fail http://localhost:8080/ || exit 1

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
