package com.yLobanov.sensor.controllers;

import com.yLobanov.sensor.dto.MeasurementDTO;
import com.yLobanov.sensor.models.Measurement;
import com.yLobanov.sensor.services.MeasurementService;
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

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final SensorValidator sensorValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorValidator sensorValidator) {
        this.measurementService = measurementService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult result) {
        Measurement measurement = convertToMeasurement(measurementDTO);
        sensorValidator.validateMeasurement(measurement.getSensor(), result);
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
        measurementService.createMeasurement(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurement.setTime(new Date());
        return measurement;
    }


}
