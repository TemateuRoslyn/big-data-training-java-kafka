package com.softmaes.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Properties;

import java.time.Duration;


public class SM1Consumer {

    // attributes

    public Properties properties;
    public KafkaConsumer<String, String> kafkaConsumer;

    public String groupId;
    public String serverHost;


    /**
     * No arguments constructor
     */
    public SM1Consumer(String groupId, String serverHost){

        System.out.println("================ apppel du consommateur ==========================");

        this.groupId = groupId;
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

        this.properties.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        this.properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.serverHost);
        this.properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        this.properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        this.properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        this.properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    }

    /**
     * initialize the kafka consumer
     */
    public void initKafkaConsumer(){
        this.kafkaConsumer = new KafkaConsumer<String, String>(this.properties);
        this.kafkaConsumer.subscribe(Collections.singletonList("maes-topic-one"));
    }
    
    /**
     * display topics messages
     */
    public void streamConsumer(){
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
             ConsumerRecords<String, String> consumerRecords = this.kafkaConsumer.poll(Duration.ofMillis(10)); // si 1000 sont produit par second on en recupere 10
             consumerRecords.forEach(cr -> {
                System.out.println("{\tkey => " + cr.key() + ", " + cr.value() + " => " + cr.offset() + "\t}\n");
             });
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }
    
}
