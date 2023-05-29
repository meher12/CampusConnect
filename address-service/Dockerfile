FROM openjdk:17
ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} name.jar
COPY /target/${JAR_FILE} address-service.jar
ENTRYPOINT ["java", "-jar","address-service.jar"]
EXPOSE 8281