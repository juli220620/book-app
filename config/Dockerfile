FROM alpine/java:21-jdk
COPY build/libs/app.jar /app/
ADD files /app/files
WORKDIR /app/
ENTRYPOINT ["java", "-jar", "app.jar"]