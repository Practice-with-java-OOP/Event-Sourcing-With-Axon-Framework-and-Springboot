package com.syphan.practice.eventsourcingaxonspringboot.event;

public class BaseEvent<T> {

    public final T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
