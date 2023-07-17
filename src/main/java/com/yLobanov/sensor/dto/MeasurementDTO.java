package com.yLobanov.sensor.dto;

import com.yLobanov.sensor.models.Sensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class MeasurementDTO {

    @Min(value = -100, message = "Measurement is incorrect, minimum temperature is -100")
    @Max(value = 100, message = "Measurement is incorrect, maximum temperature is 100")
    private int value;

    @NotEmpty(message = "you need to add whether it is raining")
    private boolean isRaining;

    @NotEmpty(message = "you need to add sensor's name")
    private Sensor sensor;
}
