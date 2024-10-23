FROM gradle:8.9.0-jdk21 AS build
WORKDIR /app
COPY . . 
RUN gradle build
FROM amazoncorretto:21.0.4

ARG JAR_FILE=build/libs/*.jar

COPY --from=build /app/$JAR_FILE ./app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","./app.jar"]
