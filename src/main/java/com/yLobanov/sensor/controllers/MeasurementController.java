package com.yLobanov.sensor.controllers;

import com.yLobanov.sensor.dto.MeasurementDTO;
import com.yLobanov.sensor.models.Measurement;
import com.yLobanov.sensor.models.Sensor;
import com.yLobanov.sensor.services.MeasurementService;
import com.yLobanov.sensor.services.SensorService;
import com.yLobanov.sensor.utils.MeasurementIncorrectException;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final SensorValidator sensorValidator;
    private final SensorService sensorService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorValidator sensorValidator, SensorService sensorService) {
        this.measurementService = measurementService;
        this.sensorValidator = sensorValidator;
        this.sensorService = sensorService;
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
            throw new MeasurementIncorrectException(errors.toString());
        }
        measurementService.createMeasurement(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(MeasurementIncorrectException exception) {
        SensorErrorResponse response = new SensorErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SensorNotFoundException.class)
    private ResponseEntity<SensorErrorResponse> handleException() {
        SensorErrorResponse response = new SensorErrorResponse(
                "Such sensor does not exist!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = new ModelMapper().map(measurementDTO, Measurement.class);
        measurement.setTime(new Date());
        measurement.setSensor(sensorService.findOneByNameOrElseThrowException(measurement.getSensor().getName()));
        return measurement;
    }

    @GetMapping
    public List<String> getMeasurements() {
        List<String> stringMeasurements = new ArrayList<String>();
        List<Measurement> measurements = measurementService.getAllMeasurements();
        for(Measurement measurement : measurements) {
            stringMeasurements.add(measurement.toString());
        }
        return stringMeasurements;
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return new ModelMapper().map(measurement, MeasurementDTO.class);
    }


}
