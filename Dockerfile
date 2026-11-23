# Etapa 1: build com Maven + JDK
FROM docker.io/maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -B -ntp -DskipTests=true

# Etapa 2: imagem final com JRE e usuário seguro
FROM docker.io/eclipse-temurin:21-jre-alpine
WORKDIR /app

# Cria um usuário não-root para segurança
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copia o jar da etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
