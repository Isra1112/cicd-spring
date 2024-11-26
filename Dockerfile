FROM eclipse-temurin:17-jdk-alpine
RUN pwd
WORKDIR /app
RUN pwd
RUN ls -l
COPY /home/runner/work/cicd-spring/cicd-spring/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]