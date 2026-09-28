package kafka.test;

import kafka.consumer.KafkaConsumerClient;
import kafka.model.Person;
import kafka.producer.Producer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class PersonTest {

    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient();
    Producer producer = new Producer();

    @Test
    public void sendTestRequest(){
        producer.send("Test",new Person(1,"Daniil", 25, "Тест описание"));
        ConsumerRecord<String,String> consumerRecord = kafkaConsumerClient.consumerRecord(Duration.ofSeconds(5));
        Assertions.assertNotNull(consumerRecord);

    }
}
