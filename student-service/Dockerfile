FROM openjdk:17
ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} name.jar
COPY /target/${JAR_FILE} student-service.jar
ENTRYPOINT ["java", "-jar","student-service.jar"]
EXPOSE 8181