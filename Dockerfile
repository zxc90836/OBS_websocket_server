FROM openjdk:17
WORKDIR /usr/src/app

COPY . .
ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-jar","./target/OBS_websocket_server-0.0.1-SNAPSHOT.jar"]