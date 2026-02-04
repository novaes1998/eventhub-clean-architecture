# ===== build =====
FROM azul/zulu-openjdk:17 AS build
WORKDIR /app

# instala Maven no estágio de build
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests package

# ===== run =====
FROM azul/zulu-openjdk:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
