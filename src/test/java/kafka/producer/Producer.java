package kafka.producer;

import kafka.config.KafkaConfig;
import kafka.model.Person;
import kafka.utils.JsonUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import tools.jackson.databind.ser.jdk.StringSerializer;

import java.util.Properties;

public class Producer implements AutoCloseable {

    KafkaProducer<String, String> kafkaProducer;

    public Producer(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAP_SERVER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducer = new KafkaProducer<>(properties);

    }

    public void send(String key, Person person){
        String json = JsonUtil.toJson(person);
        kafkaProducer.send(new ProducerRecord<>(KafkaConfig.TOPIC, key, json));
    }

    @Override
    public void close() throws Exception {
        kafkaProducer.close();
    }
}
