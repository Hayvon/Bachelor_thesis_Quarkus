#!/bin/sh
cd ../../
docker build -f docker/app/Dockerfile . -t quakrus
docker run --name Bachelorarbeit_Quarkus -v C:/dockerVolume:/mnt -p 9090:9090 quakrus