FROM eclipse-temurin:17-jdk-alpine

COPY . .

RUN ./mvnw clean package -Dmaven.test.skip

CMD java -jar ./target/typing-speed-test-1.0.0.jar

EXPOSE 8080