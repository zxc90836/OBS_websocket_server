FROM openjdk:17
WORKDIR /usr/src/app

COPY . .
ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-jar","./target/demo-0.0.1-SNAPSHOT.jar"]