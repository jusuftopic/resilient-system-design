package org.example.eventconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaRetryTopic;

@SpringBootApplication
@EnableKafka
@EnableKafkaRetryTopic
public class EventConsumerApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(EventConsumerApplication.class, args);
    }

}
