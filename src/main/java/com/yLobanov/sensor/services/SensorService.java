package com.yLobanov.sensor.services;

import com.yLobanov.sensor.models.Sensor;
import com.yLobanov.sensor.repository.SensorRepository;
import com.yLobanov.sensor.utils.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public void createSensor(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public Sensor loadSensorBySensorName(String sensor_name) throws SensorNotFoundException {
        Optional<Sensor> sensor = sensorRepository.findByName(sensor_name);
        if(sensor.isEmpty()) throw new SensorNotFoundException("Sensor was not found");
        return sensor.get();
    }
}
