#!/bin/sh

cd src/backend/java
mvn clean package
java -jar target/big-data-training-java-kafka-1.0-SNAPSHOT.jar