package com.yLobanov.sensor.utils;

import com.yLobanov.sensor.models.Sensor;
import com.yLobanov.sensor.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        try {
            sensorService.loadSensorBySensorName(sensor.getName());
        } catch (SensorNotFoundException ignored) {
            return; // Ok, sensor was not created before
        }

        errors.rejectValue("sensor_name", "", "Sensor with such username already exists");
    }
}
