# Используем базовый образ OpenJDK
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar файл вашего приложения в контейнер
COPY target/freshMeet-0.0.1-SNAPSHOT.jar /app/freshMeet-0.0.1-SNAPSHOT.jar

# Открываем порт, на котором будет работать ваше приложение
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "freshMeet-0.0.1-SNAPSHOT.jar"]