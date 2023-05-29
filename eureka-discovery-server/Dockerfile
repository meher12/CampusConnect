FROM openjdk:17
ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} name.jar
RUN mvn clean install
COPY /target/${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java", "-jar","eureka-server.jar"]
EXPOSE 8761