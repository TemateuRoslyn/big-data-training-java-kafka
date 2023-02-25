FROM maven:3.6.3-openjdk-8 AS build
WORKDIR /app
COPY . /app/ 
RUN mvn -B clean -DskipTests 
RUN mvn -B package -DskipTests 
EXPOSE 8088  
CMD ["java","-jar","target/big-data-training-java-kafka-1.0-SNAPSHOT.jar"] 