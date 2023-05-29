FROM openjdk:17
ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} name.jar
COPY /target/${JAR_FILE} api-gateway.jar
ENTRYPOINT ["java", "-jar","api-gateway.jar"]
EXPOSE 8765