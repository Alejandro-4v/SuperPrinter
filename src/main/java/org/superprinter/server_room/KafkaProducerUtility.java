package org.superprinter.server_room;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.superprinter.utils.Finals;

import java.util.Properties;

public class KafkaProducerUtility {

    private static KafkaProducer<String, String> producer;

    public static void initialize() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Finals.KAFKA_BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
    }

    public static void sendMessage(String topic, String key, String message) {
        producer.send(new ProducerRecord<>(topic, key, message));
    }
}
