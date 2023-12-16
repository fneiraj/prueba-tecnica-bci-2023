FROM maven:3.8.4 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package 

FROM eclipse-temurin:17.0.9_9-jre-ubi9-minimal

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/pruebabci-0.0.1-SNAPSHOT.jar /opt/app/

ENTRYPOINT ["java","-jar", "pruebabci-0.0.1-SNAPSHOT.jar"]