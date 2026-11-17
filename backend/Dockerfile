# ===================================================================
# Etapa 1: Compilación con Maven y OpenJDK 17
# ===================================================================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copiar descriptor de dependencias
COPY pom.xml .

# Descarga anticipada de dependencias para optimizar la caché de capas Docker
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar el artefacto JAR omitiendo tests
RUN mvn clean package -DskipTests -B

# ===================================================================
# Etapa 2: Imagen ligera de ejecución (JRE 17)
# ===================================================================
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Metadatos del contenedor
LABEL maintainer="DevOps Práctica S11"
LABEL description="Spring Boot WebFlux Backend conectado a MongoDB en Kubernetes"

# Copiar el JAR generado en la etapa de construcción
COPY --from=builder /workspace/target/*.jar app.jar

# Exponer el puerto configurado en el backend
EXPOSE 8080

# Parámetros óptimos para contenedores Java
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Punto de entrada de la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

