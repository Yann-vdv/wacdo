FROM jelastic/maven:3.9.5-openjdk-21 AS BUILD
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
COPY --from=build /target/wacdo-0.0.1-SNAPSHOT.jar wacdo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","wacdo.jar"]