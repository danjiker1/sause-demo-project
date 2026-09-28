package kafka.consumer;

import kafka.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Timeout;
import tools.jackson.databind.deser.jdk.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class KafkaConsumerClient implements AutoCloseable {

    KafkaConsumer<String, String> kafkaConsumer;

    public KafkaConsumerClient(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVER);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConfig.CONSUMER_GROUP);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(List.of(KafkaConfig.TOPIC));
    }

    public ConsumerRecords<String, String> poll(Duration timeout){
        return kafkaConsumer.poll(timeout);

    }

    public ConsumerRecord<String, String> consumerRecord(Duration duration){
        return kafkaConsumer.poll((duration)).iterator().next();
    }


    @Override
    public void close() throws Exception {
        kafkaConsumer.close();
    }
}
