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
    public void validate(Object object, Errors errors) {
        Sensor sensor = (Sensor) object;
        if (sensorService.findOneByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Such sensor is already registered!");
        }
    }

    public void validateMeasurement(Object object, Errors errors) {
        Sensor sensor = (Sensor) object;
        if (sensorService.findOneByName(sensor.getName()).isEmpty()) {
            errors.rejectValue("name", "", "No such sensor exists for this measurement!");
        }
    }
}
