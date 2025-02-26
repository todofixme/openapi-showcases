#!/bin/sh

mvn clean verify -DskipTests -PdocGen
cp -pr target/docs target/classes/.

mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imagePlatform=linux/arm64
docker tag todofixme/shelf-patrol-java-spring:latest todofixme/shelf-patrol-java-spring:arm64
docker push todofixme/shelf-patrol-java-spring:arm64

mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.builder=bellsoft/buildpacks.builder:musl
docker push todofixme/shelf-patrol-java-spring:latest
