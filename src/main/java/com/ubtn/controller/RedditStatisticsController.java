package com.ubtn.controller;

import com.ubtn.service.RedditService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RedditStatisticsController {

    @Autowired
    private RedditService redditService;

    @GetMapping("/api/count")
    @ApiOperation(value = "count stream")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    public ResponseEntity count(TimeRange timeRange) {
        if(timeRange == null) {
            timeRange = TimeRange.ALL_TIME;
        }
        switch (timeRange) {
            case ONE_MINUTE:
                return ResponseEntity.ok(redditService.getOneMinuteCount());
            case FIVE_MINUTES:
                return ResponseEntity.ok(redditService.getFiveMinuteCount());
            case ONE_HOUR:
                return ResponseEntity.ok(redditService.getOneHourCount());
            case ONE_DAY:
                return ResponseEntity.ok(redditService.getOneDayCount());
            case ALL_TIME:
                return ResponseEntity.ok(redditService.getAllTimeCount());
            default:
                return ResponseEntity.ok(redditService.getAllTimeCount());
        }
    }

    @GetMapping("/api/top-hundred-active")
    @ApiOperation(value = "top hundred")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    public ResponseEntity topHundredActive(TimeRange timeRange) {
        if(timeRange == null) {
            timeRange = TimeRange.ALL_TIME;
        }

        switch (timeRange) {
            case ONE_MINUTE:
                return ResponseEntity.ok(redditService.getOneMinuteTop());
            case FIVE_MINUTES:
                return ResponseEntity.ok(redditService.getFiveMinuteTop());
            case ONE_HOUR:
                return ResponseEntity.ok(redditService.getOneHourTop());
            case ONE_DAY:
                return ResponseEntity.ok(redditService.getOneDayTop());
            case ALL_TIME:
                return ResponseEntity.ok(redditService.getAllTimeTop());
            default:
                return ResponseEntity.ok(redditService.getAllTimeTop());
        }
    }

    @GetMapping("/api/top-active-count")
    @ApiOperation(value = "top active count")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    public ResponseEntity topActiveCount(TimeRange timeRange) {
        if(timeRange == null) {
            timeRange = TimeRange.ALL_TIME;
        }

        switch (timeRange) {
            case ONE_MINUTE:
                List oneMinuteTop = redditService.getOneMinuteTop();
                if(oneMinuteTop.isEmpty()) {
                    return ResponseEntity.ok(SubReddit.builder().count(0));
                }
                return ResponseEntity.ok(oneMinuteTop.get(0));
            case FIVE_MINUTES:
                List fiveMinuteTop = redditService.getFiveMinuteTop();
                if(fiveMinuteTop.isEmpty()) {
                    return ResponseEntity.ok(SubReddit.builder().count(0));
                }
                return ResponseEntity.ok(fiveMinuteTop.get(0));
            case ONE_HOUR:
                List oneHourTop = redditService.getOneHourTop();
                if(oneHourTop.isEmpty()) {
                    return ResponseEntity.ok(SubReddit.builder().count(0));
                }
                return ResponseEntity.ok(oneHourTop.get(0));
            case ONE_DAY:
                List onedayTop = redditService.getOneHourTop();
                if(onedayTop.isEmpty()) {
                    return ResponseEntity.ok(SubReddit.builder().count(0));
                }
                return ResponseEntity.ok(onedayTop.get(0));
            case ALL_TIME:
                List allTimeTop = redditService.getOneHourTop();
                if(allTimeTop.isEmpty()) {
                    return ResponseEntity.ok(SubReddit.builder().count(0));
                }
                return ResponseEntity.ok(allTimeTop.get(0));
            default:
                List allTimeTopDef = redditService.getOneHourTop();
                if(allTimeTopDef.isEmpty()) {
                    return ResponseEntity.ok(SubReddit.builder().count(0));
                }
                return ResponseEntity.ok(allTimeTopDef.get(0));        }
    }

    enum TimeRange {
        ONE_MINUTE, FIVE_MINUTES, ONE_HOUR, ONE_DAY,ALL_TIME,
    }

}
