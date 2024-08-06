FROM eclipse-temurin:21-jdk-alpine AS build

COPY . .

RUN ./mvnw clean package -Dmaven.test.skip

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /target/typing-speed-test-1.0.0.jar .

CMD java -jar ./typing-speed-test-1.0.0.jar

EXPOSE 8080
