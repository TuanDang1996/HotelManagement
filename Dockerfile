FROM openjdk:17.0.2-jdk
EXPOSE 8080
RUN mkdir /app
COPY ./build/libs/hotelManagement-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions","-jar", "-Dspring.profiles.active=dev","/app/spring-boot-application.jar"]