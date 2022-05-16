package org.richard.event;

import io.micronaut.configuration.kafka.ConsumerAware;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.configuration.kafka.exceptions.KafkaListenerException;
import io.micronaut.configuration.kafka.exceptions.KafkaListenerExceptionHandler;
import jakarta.inject.Singleton;
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

    private Consumer<String, byte[]> consumer;

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
            System.out.println("Exception caught record [Key: " + consumerRecord1.key() + ", Record: "
                + new String(consumerRecord1.value()) + "]");
            System.out.println("Got Exception -> " + exception.getMessage());
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
}
