#!/bin/sh
cd ../../
docker build -f docker/native/Dockerfile . -t quarkus_bachelorarbeit && docker run -p 9090:9090 quarkus_bachelorarbeit
##docker run --name quarkus_bachelorarbeit -v C:/dockerVolume:/mnt -p 8080:8080 quarkus_bachelorarbeit