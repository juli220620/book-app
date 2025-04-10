FROM alpine/java:21-jdk
COPY build/libs/app.jar /app/
WORKDIR /app/
ENTRYPOINT ["java", "-jar", "app.jar"]