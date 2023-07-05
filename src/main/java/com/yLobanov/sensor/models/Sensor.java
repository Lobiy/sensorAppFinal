package com.yLobanov.sensor.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "sensor")
@Getter
@Setter
public class Sensor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sensor_name")
    @NotEmpty(message = "Sensor name can't be empty!")
    @Size(min = 2, max = 30, message = "Name can't be longer than 30 symbols")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private Set<Measurement> measurementSet;
}
