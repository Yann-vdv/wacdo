FROM jelastic/maven:3.9.5-openjdk-21 AS BUILD
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/target/*.jar wacdo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","wacdo.jar"]