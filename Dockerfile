FROM maven:3.9-eclipse-temurin-21-jammy AS builder
WORKDIR /app

COPY --chmod=0755 mvnw mvnw
COPY . .

RUN mvn clean package -DskipTests

RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests


FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN apt-get update \
  && apt-get install -y ffmpeg --no-install-recommends \
  && rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
