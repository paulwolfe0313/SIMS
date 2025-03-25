FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Add wait-for-it script to wait for dependent services
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Wait for MySQL and Kafka to be ready before starting the application
CMD ["/bin/sh", "-c", "/wait-for-it.sh mysql-sims:3306 -t 60 && /wait-for-it.sh kafka:9092 -t 60 && java -jar app.jar"]

EXPOSE 9095
