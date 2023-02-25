package com.softmaes;

import com.softmaes.consumers.SM1Consumer;
import com.softmaes.producers.SM1Producer;

/**
 * Hello world!
 *
 */
public class BigDataTrainingJavaApplication 
{
    public static void main( String[] args )
    {
        //creation du producteur
        SM1Producer sm1producer = new SM1Producer("localhost:9092");

        // creation du consomateur
        SM1Consumer sm1Consumer = new SM1Consumer("sm-group", "localhost:9092");
    }
}
