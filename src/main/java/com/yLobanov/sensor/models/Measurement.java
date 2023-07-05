package com.yLobanov.sensor.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "measurement")
@Getter
@Setter
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @Min(value = -100, message = "Measurement is incorrect, minimum temperature is -100")
    @Max(value = 100, message = "Measurement is incorrect, maximum temperature is 100")
    private int value;

    @Column(name = "raining")
    private boolean isRaining;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "measurement_time")
    private Date measurementTime;

    @ManyToOne
    @JoinColumn(name="sensor_name", nullable=false)
    private Sensor sensor;
}
