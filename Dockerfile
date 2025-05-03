# ===== Build Stage =====
FROM gradle:8.5.0-jdk17-alpine AS build

WORKDIR /app

# Копируем только конфигурационные файлы
COPY app/build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .
COPY gradle gradle

# Кэширование зависимостей
RUN gradle dependencies --configuration runtimeClasspath --no-daemon

# Копируем исходный код
COPY app/src app/src

# Сборка приложения
RUN gradle clean shadowJar --no-daemon

# ===== Runtime Stage =====
FROM eclipse-temurin:17-jre-alpine

# Установка curl для healthcheck
RUN apk add --no-cache curl

# Создание не-root пользователя
RUN addgroup -S appuser && adduser -S appuser -G appuser
USER appuser

WORKDIR /home/appuser/app

# Копирование артефакта сборки
COPY --from=build --chown=appuser:appuser /app/app/build/libs/app-*.jar ./app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=3s CMD curl --fail http://localhost:8080/ || exit 1

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
