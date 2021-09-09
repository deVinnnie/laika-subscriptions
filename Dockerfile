FROM openjdk:11-jre-slim
COPY target/laika-subscriptions-exec.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=production,smtp","-jar","/app.jar"]
