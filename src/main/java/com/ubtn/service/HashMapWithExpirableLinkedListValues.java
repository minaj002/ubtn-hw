package com.ubtn.service;

import com.ubtn.controller.SubReddit;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HashMapWithExpirableLinkedListValues {

    private HashMap<String, ExpirableLinkedList> hashMap = new HashMap<>();
    private TimeUnit ttlUnit;
    private long ttlValue;

    public HashMapWithExpirableLinkedListValues(TimeUnit ttlUnit, long ttlValue) {
        this.ttlUnit = ttlUnit;
        this.ttlValue = ttlValue;
    }

    public void put(String key) {

        if(hashMap.containsKey(key)) {
            hashMap.get(key).add();
        } else {
            ExpirableLinkedList list = new ExpirableLinkedList(ttlUnit, ttlValue);
            list.add();
            hashMap.put(key, list);
        }
    }

    public List getOrdered() {

        removeExpired();
        HashMap<String, ExpirableLinkedList> clonedMap = (HashMap<String, ExpirableLinkedList>) hashMap.clone();

        Map<String, Integer> nameToSizeMap = clonedMap.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue().size()));
        Comparator<? super Map.Entry<String, Integer>> comparator = (o1, o2) -> o2.getValue().compareTo(o1.getValue());
        List<SubReddit> orderedList = nameToSizeMap.entrySet().stream().sorted(comparator).limit(100).map(entry -> SubReddit.builder().name(entry.getKey()).count(entry.getValue()).build()).collect(Collectors.toList());

        return orderedList;
    }

    private void removeExpired() {
        hashMap.forEach((key, entry) -> entry.removeExpired());
    }


}