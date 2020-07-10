FROM openjdk:8
WORKDIR /home/app/quarkus
COPY target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar /home/app/quarkus/build/app.jar
#RUN java -jar build/app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","build/app.jar"]