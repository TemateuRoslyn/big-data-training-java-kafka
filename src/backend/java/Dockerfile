FROM maven:3.6.3-openjdk-8 AS build
WORKDIR /app
COPY . /app/ 
RUN mvn -f /app/pom.xml clean package

# COPY /app/target/big-data-training-java-kafka-1.0-SNAPSHOT.jar /app/big-data-training-java-kafka-1.0-SNAPSHOT.jar  
EXPOSE 8088  
# CMD ["java","-jar","/app/big-data-training-java-kafka-1.0-SNAPSHOT.jar"] 