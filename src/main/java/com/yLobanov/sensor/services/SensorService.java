package com.yLobanov.sensor.services;

import com.yLobanov.sensor.models.Sensor;
import com.yLobanov.sensor.repository.SensorRepository;
import com.yLobanov.sensor.utils.MeasurementIncorrectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;
    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void createSensor(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public Optional<Sensor> findOneByName(String name) {
        return sensorRepository.findByName(name);
    }

    public Sensor findOneByNameOrElseThrowException(String name) {
        return sensorRepository.findByName(name).orElseThrow(MeasurementIncorrectException::new);
    }

}
