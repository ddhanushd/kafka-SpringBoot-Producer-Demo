package com.kafka.kafkaproducerexample.service;

import com.kafka.kafkaproducerexample.dto.Customer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagepublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaMessagepublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageToTopic(String message){
//        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("DhanushTopic", 3, null, message);
//        future.whenComplete((result,ex)->{
//            if (ex == null) {
//                System.out.println("Sent message=[" + message +
//                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            } else {
//                System.out.println("Unable to send message=[" +
//                        message + "] due to : " + ex.getMessage());
//            }
//        });
        kafkaTemplate.send("DemoTopic", 0, null, "hi");
        kafkaTemplate.send("DemoTopic", 1, null, "hello");
        kafkaTemplate.send("DemoTopic", 2, null, "welcome");
        kafkaTemplate.send("DemoTopic", 2, null, "hey");
        kafkaTemplate.send("DemoTopic", 3, null, "superb");

    }

    public void sendEventsToTopic(Customer customer) {
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("KafkaTopic-demo", customer);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + customer.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            customer.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
