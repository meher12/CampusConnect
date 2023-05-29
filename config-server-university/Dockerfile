FROM openjdk:17
ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} name.jar
COPY /target/${JAR_FILE} config-server.jar
ENTRYPOINT ["java", "-jar","config-server.jar"]
EXPOSE 8888