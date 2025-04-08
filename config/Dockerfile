FROM gradle:jdk21
COPY build/libs/app.jar /app/
ADD files /app/files
WORKDIR /app/
ENTRYPOINT ["java", "-jar", "app.jar"]