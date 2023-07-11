package com.yLobanov.sensor.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDTO {

    @Size(min = 2, max = 30, message = "Name can't be longer than 30 symbols")
    private String name;

}