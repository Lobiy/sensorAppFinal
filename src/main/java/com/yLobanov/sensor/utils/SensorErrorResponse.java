package com.yLobanov.sensor.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorErrorResponse {
    private String message;
    private long timestamp;

    public SensorErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
