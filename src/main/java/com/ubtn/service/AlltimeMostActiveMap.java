package com.ubtn.service;

import com.ubtn.controller.SubReddit;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class AlltimeMostActiveMap {

    private HashMap<String, Integer> hashMap = new HashMap<>();

    public void put(String key) {

        if(hashMap.containsKey(key)) {
            Integer value = hashMap.get(key);
            hashMap.replace(key, ++ value);
        } else {
            hashMap.put(key, 1);
        }
    }

    public List getOrdered() {

        HashMap<String, Integer> clonedMap = (HashMap<String, Integer>) hashMap.clone();

        Comparator<? super Map.Entry<String, Integer>> comparator = (o1, o2) -> o2.getValue().compareTo(o1.getValue());
        List<SubReddit> orderedList = clonedMap.entrySet().stream()
                .sorted(comparator).limit(100)
                .map(entry -> SubReddit.builder().name(entry.getKey()).count(entry.getValue()).build())
                .collect(Collectors.toList());

        return orderedList;
    }

}