package com.example.streamsproto;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Instant;
import java.util.Properties;

public class SimpleMessageProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProtobufSerializer.class);
        properties.put(KafkaProtobufSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        Producer<String, Simplemessage.SimpleMessage> producer = new KafkaProducer<>(properties);

        Simplemessage.SimpleMessage simplemessage = Simplemessage.SimpleMessage.newBuilder()
                .setContent("Dimelooo!!!")
                .setDateTime(Instant.now().toString())
                .build();
        ProducerRecord<String, Simplemessage.SimpleMessage> record = new ProducerRecord<>("simple_message", null, simplemessage);
        producer.send(record);
        producer.flush();
        producer.close();
    }
}
