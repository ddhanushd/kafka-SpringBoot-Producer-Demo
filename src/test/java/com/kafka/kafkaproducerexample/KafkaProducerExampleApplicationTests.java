package com.kafka.kafkaproducerexample;

import com.kafka.kafkaproducerexample.dto.Customer;
import com.kafka.kafkaproducerexample.service.KafkaMessagepublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class KafkaProducerExampleApplicationTests {

    @Container
    static KafkaContainer kafka =
            new KafkaContainer(
                    DockerImageName.parse("apache/kafka:3.8.0")
            );

    @DynamicPropertySource
    public static void initKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private KafkaMessagepublisher publisher;

    @Test
    public void testSendEventsToTopic() {
        publisher.sendEventsToTopic(new Customer(263, "test user", "test@gmail.com", "564782542752"));
        await().pollInterval(Duration.ofSeconds(3))
                .atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
                    // assert statement
                });
    }

}
