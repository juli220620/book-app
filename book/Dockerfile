FROM alpine/java:21-jdk
COPY build/libs/app.jar /app/
ADD keys /app/keys
WORKDIR /app/
ENTRYPOINT ["java", "-jar", "app.jar"]