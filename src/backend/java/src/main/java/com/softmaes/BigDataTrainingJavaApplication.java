package com.softmaes;

import com.softmaes.consumers.SmartOneConsumer;

import com.softmaes.consumers.MaesOneConsumer;
import com.softmaes.consumers.MaesTwoConsumer;

import com.softmaes.producers.SM1Producer;
import com.softmaes.producers.SM2Producer;

/**
 * java kafka testing app
 *
 */
public class BigDataTrainingJavaApplication 
{
    public static void main( String[] args )
    {
        // broker hostname
        String broker_one = "localhost:9092";

        // topics
        String topic_1 = "maes-topic-one"; // un topic avec une seule partition
        String topic_2 = "maes-topic-two"; // un topic avec deux partions

        // consumer groups
        String maes_group = "maes_consumer_group";
        String smart_group = "smart_consumer_group";

        // producers
        new SM1Producer(broker_one, topic_1);
        new SM2Producer(broker_one, topic_2);

        // maes consumers group 
        new MaesOneConsumer(maes_group, broker_one, topic_2);
        new MaesTwoConsumer(maes_group, broker_one, topic_2);
        
        // smart consumers group 
        new SmartOneConsumer(smart_group, broker_one, topic_1);
    }
}
