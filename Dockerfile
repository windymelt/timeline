FROM openjdk:8
RUN mkdir /app

# Generate this jar file with sbt clean assembly.
ADD target/scala-2.13/Timeline-assembly-0.1.0-SNAPSHOT.jar /app/app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]