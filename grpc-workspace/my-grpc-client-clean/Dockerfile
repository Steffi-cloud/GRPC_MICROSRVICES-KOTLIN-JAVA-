FROM eclipse-temurin:21-jre-alpine

# this is where the app will live *inside* the container
WORKDIR /my-grpc-client-clean

# copy the built jar from the host into the container as app.jar
#COPY build/libs/*.jar app.jar
COPY build/install/my-grpc-client-clean/ ./
# if later you make it a server, you can add: EXPOSE 50051

#ENTRYPOINT ["java", "-jar", "/my-grpc-client-clean/app.jar"]
ENTRYPOINT ["./bin/my-grpc-client-clean"]



