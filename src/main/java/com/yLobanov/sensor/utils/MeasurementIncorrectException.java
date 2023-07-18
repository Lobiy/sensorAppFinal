package com.yLobanov.sensor.utils;

public class MeasurementIncorrectException extends RuntimeException{
    public MeasurementIncorrectException(String message) {
        super(message);
    }

    public MeasurementIncorrectException() {
    }
}
