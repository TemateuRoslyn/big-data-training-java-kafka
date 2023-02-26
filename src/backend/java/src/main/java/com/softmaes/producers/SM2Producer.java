package com.softmaes.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Properties;
import java.util.Random;

import java.time.Duration;

public class SM2Producer {
        // attributes

        public Properties properties;
        public KafkaProducer<String, String> kafkaProducer;
        public ProducerRecord<String, String> producerRecord;
    
        public String serverHost;
        public String topic_name;

        public int message_no;
    
    
        /**
         * No arguments constructor
         */
        public SM2Producer(String serverHost, String topic_name){
    
            this.serverHost = serverHost;
            this.topic_name = topic_name;
            this.message_no = 0;
    
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
            this.properties.put(ProducerConfig.CLIENT_ID_CONFIG, "client-producer-2");
            this.properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            this.properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
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
                String value = String.valueOf("from_prod_2" + "__" + (++this.message_no));

                this.producerRecord =new ProducerRecord<>(this.topic_name, key, value);

                this.kafkaProducer.send(this.producerRecord, (metadata, ex) -> {
                    System.out.println("Producer SM2: Sending message => " 
                    + value + ", Partition => " 
                    + metadata.partition()
                    + ", Offset => " + metadata.offset());
                });
            }, 1000, 1000, TimeUnit.MILLISECONDS);
        }
    }
