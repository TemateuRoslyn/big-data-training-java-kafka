package com.softmaes.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Properties;
import java.util.Random;
import java.time.Duration;

public class SM1Producer {
        // attributes

        public Properties properties;
        public KafkaProducer<String, String> kafkaProducer;
    
        public String serverHost;
    
    
        /**
         * No arguments constructor
         */
        public SM1Producer(String serverHost){
    
            this.serverHost = serverHost;
    
            this.initProperties();
            this.initKafkaProducer();
            this.streamProducer();
        }
    
    
        /**
         * initialize consumer properties
         */
        public void initProperties(){
    
            this.properties = new Properties();
    
            this.properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.serverHost);
            this.properties.put(ProducerConfig.CLIENT_ID_CONFIG, "client-producer-1");
            this.properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            this.properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        }
    
        /**
         * initialize the kafka consumer
         */
        public void initKafkaProducer(){
            this.kafkaProducer = new KafkaProducer<String, String>(this.properties);
        }
        
        /**
         * write message to topic (inside a partition)
         */
        public void streamProducer(){ 
            Random random = new Random();
            Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
                String key = String.valueOf(random.nextInt(1000));
                String value = String.valueOf(random.nextDouble()*999999);
                this.kafkaProducer.send(new ProducerRecord<String, String>("test1", key, value), (metadata, ex) -> {
                    System.out.println("Metadata: Sending message => " 
                    + value + ", Partition => " 
                    + metadata.partition()
                    + ", Offset => " + metadata.offset());
                });
            }, 1000, 1000, TimeUnit.MILLISECONDS);
        }
}
