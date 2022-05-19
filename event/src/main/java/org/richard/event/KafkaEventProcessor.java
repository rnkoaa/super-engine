package org.richard.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.configuration.kafka.ConsumerAware;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.configuration.kafka.exceptions.KafkaListenerException;
import io.micronaut.configuration.kafka.exceptions.KafkaListenerExceptionHandler;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;

@Singleton
@KafkaListener(groupId = "${kafka.event.group-id}")
public class KafkaEventProcessor implements ConsumerRebalanceListener, ConsumerAware<String, byte[]>,
    KafkaListenerExceptionHandler {

    private final ObjectMapper objectMapper;
    private Consumer<String, byte[]> consumer;

    private final KafkaDeadLetterEventPublisher deadLetterEventPublisher;
    private final KafkaPoisonEventPublisher poisonEventPublisher;

    public KafkaEventProcessor(ObjectMapper objectMapper,
        KafkaDeadLetterEventPublisher deadLetterEventPublisher,
        KafkaPoisonEventPublisher poisonEventPublisher) {
        this.objectMapper = objectMapper;
        this.deadLetterEventPublisher = deadLetterEventPublisher;
        this.poisonEventPublisher = poisonEventPublisher;
    }

    @Topic("${kafka.event.topic}")
    public void receive(ConsumerRecord<String, byte[]> records) throws Exception {
        byte[] value = records.value();
        String key = records.key();
        System.out.println("Key: " + key + ", value: " + new String(value));
        int keyId = Integer.parseInt(key);
        if (keyId > 1) {
            throw new PoisonException(value, "Key Id unexpected: " + keyId);
        }

        consumer.commitSync(Collections.singletonMap(
            new TopicPartition(records.topic(), records.partition()),
            new OffsetAndMetadata(records.offset() + 1, "my metadata")
        ));

//        }
    }

    @Topic("${kafka.event.poison.topic}")
    public void receivePoisonMessage(ConsumerRecord<String, byte[]> record) {
        Header retryCount = record.headers().lastHeader("retryCount");
//        if(retryCount != null) {
//            retryCount.
//        }
    }

    @Override
    public void setKafkaConsumer(@NotNull Consumer<String, byte[]> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
// seek to offset here
        for (TopicPartition partition : partitions) {
            consumer.seek(partition, 1);
        }
    }

    @Override
    public void handle(KafkaListenerException exception) {
        Optional<ConsumerRecord<?, ?>> consumerRecord = exception.getConsumerRecord();
//        Throwable cause = exception.getCause();
        Throwable causeUsingPlainJava = findCauseUsingPlainJava(exception);
        if (PoisonException.class.isAssignableFrom(causeUsingPlainJava.getClass())) {
            System.out.println("Poison Cause: " + causeUsingPlainJava.getMessage());
        }
        if (consumerRecord.isPresent()) {
            @SuppressWarnings("unchecked")
            ConsumerRecord<String, byte[]> consumerRecord1 = (ConsumerRecord<String, byte[]>) consumerRecord.get();
            byte[] value = consumerRecord1.value();
            System.out.println("Exception caught record [Key: " + consumerRecord1.key() + ", Record: "
                + new String(value) + "]");
            System.out.println("Got Exception -> " + exception.getMessage());

//            deadLetterEventPublisher.publish(consumerRecord1.key(),
//                consumerRecord1.headers(), "");
//            try {
//                EventRecord eventRecord = deserialize(value);
//                deadLetterEventPublisher.publish(
//                    consumerRecord1.key(),
//                    eventRecord.metadata.partitionKey(),
//                    consumerRecord1.headers(),
//                    (Event) eventRecord.event
//                );
//            } catch (IOException | ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }

            exception.getKafkaConsumer()
                .commitSync(Collections.singletonMap(
                    new TopicPartition(consumerRecord1.topic(), consumerRecord1.partition()),
                    new OffsetAndMetadata(consumerRecord1.offset() + 1, "my metadata")
                ));
        } else {
            System.out.println("No Body to commit offset");
            System.out.println("Got Exception -> " + exception.getMessage());

        }


    }

    public static Throwable findCauseUsingPlainJava(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    EventRecord deserialize(byte[] value) throws IOException, ClassNotFoundException {
        JsonNode jsonNode = objectMapper.readTree(value);
        String objectType = jsonNode.get("object_type").asText();
        if (objectType == null || objectType.isEmpty()) {
            return null;
        }
        Class<?> objectClass = Class.forName(objectType);
        String data = jsonNode.get("data").asText();
        if (data == null || data.isEmpty()) {
            return null;
        }

        String metadataObject = jsonNode.get("metadata").asText();
        if (metadataObject == null || metadataObject.isEmpty()) {
            return null;
        }

        Metadata metadata = objectMapper.readValue(metadataObject, Metadata.class);
        Object dataObject = objectMapper.readValue(data, objectClass);

        return new EventRecord(
            metadata, value, dataObject, objectClass, data
        );
    }

    // Headers headers = consumeRecord.headers();
    //				HashMap<String, Object> headerMap = new HashMap<>(8);
    //				Iterator<Header> iterator = headers.iterator();
    //				while (iterator.hasNext()) {
    //					Header next = iterator.next();
    //					headerMap.put(next.key(), serializer.deserialize(next.value()));
    //				}
}
