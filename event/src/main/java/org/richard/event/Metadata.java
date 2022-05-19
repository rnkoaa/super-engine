package org.richard.event;

public record Metadata(
    String correlationId,
    String partitionKey,
    RetryPolicy retry) {

}
