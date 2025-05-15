# ---------- Этап сборки ----------
FROM gradle:8.14.0-jdk17 AS build

WORKDIR /app

# Копируем конфигурационные файлы отдельно для кэширования
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle gradle

# Копируем gradlew и устанавливаем права на выполнение
COPY gradlew gradlew
RUN chmod +x gradlew  # Устанавливаем права на выполнение для gradlew

# Устанавливаем права для всех файлов
RUN mkdir -p /home/gradle/.gradle && \
    chown -R gradle:gradle /home/gradle && \
    chown -R gradle:gradle /app

USER gradle

# Предзагрузка зависимостей через Gradle Wrapper
RUN ./gradlew dependencies --no-daemon --configuration-cache

# Копируем исходники
COPY --chown=gradle:gradle src src

# Сборка fat jar через shadowJar с использованием Gradle Wrapper
RUN ./gradlew shadowJar --no-daemon --stacktrace --configuration-cache \
    -Dorg.gradle.jvmargs="-Xmx2048m -XX:MaxMetaspaceSize=512m"

# ---------- Этап исполнения ----------
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Копируем jar из сборочного этапа
COPY --from=build /app/build/libs/*.jar app.jar

# Установка переменных окружения для JVM
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+HeapDumpOnOutOfMemoryError"

EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost:8080/health || exit 1
