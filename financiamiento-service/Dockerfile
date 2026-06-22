FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} financiamiento-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/financiamiento-service.jar","--spring.profiles.active=dev"]