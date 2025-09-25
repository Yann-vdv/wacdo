FROM jelastic/maven:3.9.5-openjdk-21 AS BUILD
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

RUN echo "DB_URL is $DB_URL"

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /target/wacdo-0.0.1-SNAPSHOT.jar wacdo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","wacdo.jar"]