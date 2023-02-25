package com.softmaes;

import com.softmaes.consumers.SM1Consumer;

/**
 * Hello world!
 *
 */
public class BigDataTrainingJavaApplication 
{
    public static void main( String[] args )
    {
        SM1Consumer sm1Consumer = new SM1Consumer("sm-group", "localhost:9092");
    }
}
