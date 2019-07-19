package com.ubtn.service;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class ExpirableLinkedList {

    private final LinkedList<Long> timestamps = new LinkedList<>();
    private final long ttl;

    public ExpirableLinkedList(TimeUnit ttlUnit, long ttlValue) {
        this.ttl = ttlUnit.toNanos(ttlValue);
    }

    public boolean removeExpired() {
        if(timestamps.isEmpty()) {
            return false;
        }
        if ((System.nanoTime() - timestamps.getLast()) > this.ttl) {
            timestamps.removeLast();
            return true;
        } else {
            return false;
        }
    }

    public void add() {
        timestamps.addFirst(System.nanoTime());
    }

    public int size() {
        return timestamps.size();
    }
}