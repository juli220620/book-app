FROM gradle:jdk21
COPY build/libs/app.jar /app/
WORKDIR /app/
ENTRYPOINT ["java", "-jar", "app.jar"]