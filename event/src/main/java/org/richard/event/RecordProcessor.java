package org.richard.event;

public abstract class RecordProcessor<T> {

    abstract void process(T record);
}
