./gradlew build
docker -H "ssh://192.168.100.2" compose down
docker -H "ssh://192.168.100.2" compose build
docker -H "ssh://192.168.100.2" compose up -d