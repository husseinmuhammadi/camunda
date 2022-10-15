FROM maven:3.6.0-jdk-11 AS build
WORKDIR /build
COPY pom.xml pom.xml
COPY src src
RUN mvn clean package

FROM openjdk:11
WORKDIR /work
COPY --from=build /build/target/demo.jar demo.jar
ENTRYPOINT ["java","-jar","demo.jar"]