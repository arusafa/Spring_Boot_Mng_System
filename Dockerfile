FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/project management system-0.0.1-SNAPSHOT.jar project management system.jar/
EXPOSE 5454
ENTRYPOINT ["java","-jar","project management system.jar"]