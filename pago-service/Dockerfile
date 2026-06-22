FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} pago-service.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","/pago-service.jar","--spring.profiles.active=dev"]