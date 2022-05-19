package org.richard.event;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaPartitionKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Headers;

@KafkaClient(
    id = "deadLetter",
    acks = KafkaClient.Acknowledge.ALL,
    properties = @Property(name = ProducerConfig.RETRIES_CONFIG, value = "5")
)
public interface KafkaDeadLetterEventPublisher {

    @Topic("${kafka.producers.deadLetter.topic}")
    <T> RecordMetadata publish(
        @KafkaKey String messageKey,
        Headers headers,
        Event<T> event);

    @Topic("${kafka.producers.deadLetter.topic}")
    <T> RecordMetadata publish(
        @KafkaKey String messageKey,
        @KafkaPartitionKey String partitionKey,
        Headers headers,
        Event<T> event);

}
