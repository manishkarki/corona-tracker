FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} corona-tracker.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","corona-tracker.jar"]
