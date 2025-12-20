FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the runnable Spring Boot gRPC server jar
COPY build/libs/my-grpc-service-clean-0.0.1-SNAPSHOT.jar app.jar

# gRPC server port
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/app.jar"]


