FROM alpine/java:21-jdk
COPY build/libs/app.jar /app/
ADD keys /app/keys
COPY image_placeholder.png /app/image_placeholder.png
WORKDIR /app/
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=127.0.0.1:8000","-jar", "app.jar"]