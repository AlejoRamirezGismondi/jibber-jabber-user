FROM openjdk:8-jre-slim
RUN mkdir /app
COPY /home/runner/work/jibber-jabber-user/jibber-jabber-user/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production","/app/spring-boot-application.jar"]
