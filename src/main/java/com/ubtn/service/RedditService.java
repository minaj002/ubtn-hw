package com.ubtn.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class RedditService {

    private Integer count = 0;
    private ExpirableLinkedList oneMinute = new ExpirableLinkedList(TimeUnit.MINUTES, 1);
    private ExpirableLinkedList fiveMinute = new ExpirableLinkedList(TimeUnit.MINUTES, 5);
    private ExpirableLinkedList oneHour = new ExpirableLinkedList(TimeUnit.HOURS, 1);
    private ExpirableLinkedList oneDay = new ExpirableLinkedList(TimeUnit.DAYS, 1);

    private AlltimeMostActiveMap allTimeMostActive = new AlltimeMostActiveMap();

    private HashMapWithExpirableLinkedListValues oneMinuteTop = new HashMapWithExpirableLinkedListValues(TimeUnit.MINUTES, 1);
    private HashMapWithExpirableLinkedListValues fiveMinuteTop = new HashMapWithExpirableLinkedListValues(TimeUnit.MINUTES, 5);
    private HashMapWithExpirableLinkedListValues oneHourTop = new HashMapWithExpirableLinkedListValues(TimeUnit.HOURS, 1);
    private HashMapWithExpirableLinkedListValues oneDayTop = new HashMapWithExpirableLinkedListValues(TimeUnit.DAYS, 1);


    public RedditService() {
        WebClient client = WebClient.create("http://stream.pushshift.io/?type=submission&type=comments");

        client.get()
                .retrieve()
                .bodyToFlux(RedditFeed.class)
                .subscribe(a -> {
                    count++;
                    oneMinute.add();
                    fiveMinute.add();
                    oneHour.add();
                    oneDay.add();
                    oneMinuteTop.put(a.getSubreddit());
                    fiveMinuteTop.put(a.getSubreddit());
                    oneHourTop.put(a.getSubreddit());
                    oneDayTop.put(a.getSubreddit());
                    allTimeMostActive.put(a.getSubreddit());
                });

    }

    public Integer getAllTimeCount() {
        return count;
    }

    public Integer getOneMinuteCount() {
        while (oneMinute.removeExpired());
        return oneMinute.size();
    }

    public Integer getFiveMinuteCount() {
        while (fiveMinute.removeExpired());
        return fiveMinute.size();
    }

    public Integer getOneHourCount() {
        while (oneHour.removeExpired());
        return oneHour.size();
    }

    public Integer getOneDayCount() {
        while (oneDay.removeExpired());
        return oneDay.size();
    }

    public List getOneMinuteTop() {
        return oneMinuteTop.getOrdered();
    }

    public List getFiveMinuteTop() {
        return fiveMinuteTop.getOrdered();
    }

    public List getOneHourTop() {
        return oneHourTop.getOrdered();
    }

    public List getOneDayTop() {
        return oneDayTop.getOrdered();
    }
    public List getAllTimeTop() {
        return allTimeMostActive.getOrdered();
    }
}
