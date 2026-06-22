FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} venta-service.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/venta-service.jar","--spring.profiles.active=dev"]