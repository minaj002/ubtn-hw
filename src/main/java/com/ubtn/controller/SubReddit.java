package com.ubtn.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubReddit {

    private String name;
    private Integer count;

}
