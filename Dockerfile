# Estágio 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Copia o pom e baixa as dependências (otimiza o cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e gera o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copia apenas o JAR gerado no estágio anterior
COPY --from=build /app/target/Lojinha-1.0-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]