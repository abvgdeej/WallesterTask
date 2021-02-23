FROM openjdk:11-jdk-slim
WORKDIR /usr/src/app
ARG JAR_FILE
ARG SERVICE_PORT
COPY ${JAR_FILE} app.jar
RUN echo $JAR_FILE && ls
EXPOSE ${SERVICE_PORT}
CMD ["java", "-jar", "app.jar"]