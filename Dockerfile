# Usamos una imagen de Java 17 (o la versión que uses)
FROM eclipse-temurin:17-jdk-alpine
# Directorio de trabajo
WORKDIR /app
# Copiamos el archivo JAR generado (ajusta el nombre si es necesario)
COPY target/*.jar app.jar
# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]