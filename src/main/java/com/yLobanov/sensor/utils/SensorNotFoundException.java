package com.yLobanov.sensor.utils;

public class SensorNotFoundException extends RuntimeException{
    public SensorNotFoundException() {
    }

    public SensorNotFoundException(String message) {
        super(message);
    }

}
