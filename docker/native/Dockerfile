FROM oracle/graalvm-ce:latest as builder
RUN gu install native-image
#RUN apk add lib32stdc++6
COPY target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar /home/app/quarkus/build/app.jar
COPY src/main/resources/META-INF/native-image/org/example/code-with-quarkus/reflect-config.json /home/app/quarkus
COPY src/main/resources/META-INF/native-image/org/example/code-with-quarkus/reflectionconfig.json /home/app/quarkus
COPY src/main/resources/META-INF/native-image/org/example/code-with-quarkus/proxy-config.json /home/app/quarkus
COPY src/main/resources/META-INF/native-image/org/example/code-with-quarkus/resource-config.json /home/app/quarkus
COPY src/main/resources/META-INF/native-image/org/example/code-with-quarkus/resourceconfig.json /home/app/quarkus
WORKDIR /home/app/quarkus
RUN native-image -jar build/app.jar
EXPOSE 9090
#ENTRYPOINT ["bash"]
ENTRYPOINT ["./micronaut"]