package org.superprinter.stationers_room;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.superprinter.utils.DocumentType;
import org.superprinter.utils.Finals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class Printer implements Runnable {

    private final String id;
    private final DocumentType type;
    private final String topic;
    private final String group;
    private final String storagePath;

    public Printer(DocumentType type) {
        this.id = "Printer-" + UUID.randomUUID().toString();
        this.type = type;
        this.topic = (type == DocumentType.BLACK) ? Finals.TOPIC_BLACK : Finals.TOPIC_COLOR;
        this.group = (type == DocumentType.BLACK) ? "group_black" : "group_color";
        this.storagePath = (type == DocumentType.BLACK) ? Finals.BLACK_PRINTS_PATH : Finals.COLOR_PRINTS_PATH;
    }

    @Override
    public void run() {
        KafkaConsumer<String, String> consumer = createConsumer();
        startListening(consumer);
    }

    private KafkaConsumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Finals.KAFKA_BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.group);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new KafkaConsumer<>(props);
    }

    private void startListening(KafkaConsumer<String, String> consumer) {
        try {
            consumer.subscribe(Collections.singletonList(this.topic));
            System.out.println("[PRINTER] " + id + " (" + type + ") is now online and waiting for pages...");
            while (!Thread.currentThread().isInterrupted()) {
                pollAndPrint(consumer);
            }
        } finally {
            consumer.close();
        }
    }

    private void pollAndPrint(KafkaConsumer<String, String> consumer) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            System.out.println("[PRINTER] " + id + " caught a page from the wire!");
            printPage(record.value());
        }
    }

    private void printPage(String message) {
        File folder = prepareStorage();
        savePageToFile(folder, message);
    }

    private File prepareStorage() {
        File folder = new File(storagePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    private void savePageToFile(File folder, String message) {
        String fileName = generateFileName();
        File file = new File(folder, fileName);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(message);
            writer.flush();
            System.out.println("[PRINTER] " + id + " has successfully printed " + fileName);
        } catch (IOException e) {
            return;
        }
    }

    private String generateFileName() {
        return "print_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8) + ".json";
    }
}
