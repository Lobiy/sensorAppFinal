package com.yLobanov.sensor.controllers;

import com.yLobanov.sensor.dto.SensorDTO;
import com.yLobanov.sensor.models.Sensor;
import com.yLobanov.sensor.services.SensorService;
import com.yLobanov.sensor.utils.SensorValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;
    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping("/registration")
    public String registerSensor() {
        return "register new sensor!";
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createNewSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult result) {
        sensorValidator.validate(convertToPerson(sensorDTO), result);
        if(result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<FieldError> errorsList = result.getFieldErrors();
            for(FieldError error : errorsList) {
                errors.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
        }
        sensorService.createSensor(convertToPerson(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToPerson(SensorDTO sensorDTO) {
        Sensor sensor = new Sensor();
        sensor.setName(sensorDTO.getName());
        return sensor;
    }

}
