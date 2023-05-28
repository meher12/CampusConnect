FROM openjdk:17
ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} kpisagile.jar
#RUN mvn clean install
COPY /target/${JAR_FILE} address-service.jar
ENTRYPOINT ["java", "-jar","address-service.jar"]
EXPOSE 8281