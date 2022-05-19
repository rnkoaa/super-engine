package org.richard.event;

public class EventRecord {

    final Class<?> objectClass;
    byte[] source;
    Event event;
    Object dataObject;
    String eventData;
    Metadata metadata;

    public EventRecord(Metadata metadata, byte[] source, Object dataObject,
        Class<?> objectClass,
        String eventData){
        this.metadata = metadata;
        this.source = source;
        this.dataObject = dataObject;
        this.eventData = eventData;
        this.objectClass = objectClass;
    }
}
