package com.softmaes.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Properties;

import java.time.Duration;


public class SmartOneConsumer {

    // attributes

    public Properties properties;
    public KafkaConsumer<String, String> kafkaConsumer;

    public String groupId;
    public String topic_name;
    public String serverHost;


    /**
     * No arguments constructor
     */
    public SmartOneConsumer(String groupId, String serverHost, String topic_name){

        this.groupId = groupId;
        this.topic_name = topic_name;
        this.serverHost = serverHost;

        this.initProperties();
        this.initKafkaConsumer();
        this.streamConsumer();
    }


    /**
     * initialize consumer properties
     */
    public void initProperties(){

        this.properties = new Properties();

        this.properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.serverHost);
        this.properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        this.properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        this.properties.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        this.properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        this.properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        this.properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    }

    /**
     * initialize the kafka consumer
     */
    public void initKafkaConsumer(){
        this.kafkaConsumer = new KafkaConsumer<String, String>(this.properties);
        this.kafkaConsumer.subscribe(Collections.singletonList(this.topic_name));
    }
    
    /**
     * display topics messages
     */
    public void streamConsumer(){
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
             ConsumerRecords<String, String> consumerRecords = this.kafkaConsumer.poll(Duration.ofMillis(10)); // si 1000 sont produit par second on en recupere 10
             consumerRecords.forEach(cr -> {
                System.out.println("Consumer Smart 1: Received message =>  {\t key => " + cr.key() + ", " + cr.value() + " => " + cr.offset() + "\t}");
             });
        }, 3000, 3000, TimeUnit.MILLISECONDS);
    }
    
}
