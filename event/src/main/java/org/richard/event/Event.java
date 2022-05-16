package org.richard.event;

import java.util.HashMap;
import java.util.Map;

public class Event<T> {

    private final T data;
    private final Map<String, String> headers;

    private final String objectType;

    public Event(T data) {
        this.headers = new HashMap<>();
        this.data = data;
        objectType = data.getClass().getCanonicalName();
    }

    public Event(Map<String, String> headers, T data) {
        this.headers = headers;
        this.data = data;
        objectType = data.getClass().getCanonicalName();
    }

    T getData() {
        return data;
    }

    public String getObjectType() {
        return objectType;
    }
}
