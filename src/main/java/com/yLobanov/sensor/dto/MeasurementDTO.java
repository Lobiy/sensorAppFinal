package com.yLobanov.sensor.dto;

import com.yLobanov.sensor.models.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {

    @Min(value = -100, message = "Measurement is incorrect, minimum temperature is -100")
    @Max(value = 100, message = "Measurement is incorrect, maximum temperature is 100")
    @NotNull(message = "you need to add temperature value")
    private int value;

    @NotNull(message = "you need to add whether it is raining")
    private boolean isRaining;

    @NotNull(message = "you need to add sensor's name")
    private Sensor sensor;
}
