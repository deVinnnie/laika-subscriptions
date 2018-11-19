FROM openjdk:11-jre-slim
COPY build/libs/mailinglist-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=production,smtp","-jar","/app.jar"]