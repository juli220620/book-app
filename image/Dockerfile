FROM gradle:jdk21
COPY build/libs/app.jar /app/
ADD keys /app/keys
COPY image_placeholder.png /app/image_placeholder.png
WORKDIR /app/
ENTRYPOINT ["java", "-jar", "app.jar"]