package com.yLobanov.sensor.controllers;

import com.yLobanov.sensor.dto.SensorDTO;
import com.yLobanov.sensor.models.Sensor;
import com.yLobanov.sensor.services.SensorService;
import com.yLobanov.sensor.utils.SensorErrorResponse;
import com.yLobanov.sensor.utils.SensorNotFoundException;
import com.yLobanov.sensor.utils.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
        Sensor sensor = convertToSensor(sensorDTO);
        sensorValidator.validate(sensor, result);
        if(result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<FieldError> errorsList = result.getFieldErrors();
            for(FieldError error : errorsList) {
                errors.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotFoundException(errors.toString());
        }
        sensorService.createSensor(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException sensorNotFoundException) {
        SensorErrorResponse response = new SensorErrorResponse(
                sensorNotFoundException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorDTO, Sensor.class);
    }

}
