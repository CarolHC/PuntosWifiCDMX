# Usar una imagen base de OpenJDK (asegúrate de usar la versión que necesites)
FROM openjdk:17-jdk-slim

# Añadir el archivo JAR al contenedor
COPY target/puntosWifiCdMx-1.0.0.jar /app/puntosWifiCdMx-1.0.0.jar

# Exponer el puerto en el que Spring Boot está escuchando
EXPOSE 8080

# Comando para ejecutar el archivo JAR cuando se inicie el contenedor
ENTRYPOINT ["java", "-jar", "/app/puntosWifiCdMx-1.0.0.jar"]
