package org.richard.event;

public class Metadata {

    private final String correlationId;

    public Metadata(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}
