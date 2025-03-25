FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
RUN apk add --no-cache maven
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Set environment variables
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-sims:3306/inventory
ENV SPRING_DATASOURCE_USERNAME=sims_user
ENV SPRING_DATASOURCE_PASSWORD=sims_password

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
