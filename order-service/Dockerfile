FROM openjdk:17-jdk-slim

WORKDIR /app

# Maven builder'dan gelen JAR dosyasını kopyala
COPY target/order-service-0.0.1-SNAPSHOT.jar app.jar

# Uygulamanın çalışacağı portu aç
EXPOSE 8084

# Uygulamayı çalıştır
CMD ["java", "-jar", "app.jar"]