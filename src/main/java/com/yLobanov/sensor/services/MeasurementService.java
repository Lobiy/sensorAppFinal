package com.yLobanov.sensor.services;

import com.yLobanov.sensor.models.Measurement;
import com.yLobanov.sensor.models.Sensor;
import com.yLobanov.sensor.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Transactional
    public void createMeasurement(Measurement measurement) {
        measurementRepository.save(measurement);
    }

    public List<Measurement> getAllMeasurements(Sensor sensor){
        return measurementRepository.findAllBySensorName(sensor.getName());
    }
}
