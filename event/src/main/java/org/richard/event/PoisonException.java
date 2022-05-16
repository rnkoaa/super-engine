package org.richard.event;

public class PoisonException extends RuntimeException {

    private final Object event;

    public PoisonException(Object event, String message) {
        super(message);
        this.event = event;
    }

    public Object getEvent() {
        return event;
    }
}
