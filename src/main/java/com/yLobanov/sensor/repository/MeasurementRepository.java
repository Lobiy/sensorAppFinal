package com.yLobanov.sensor.repository;

import com.yLobanov.sensor.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
