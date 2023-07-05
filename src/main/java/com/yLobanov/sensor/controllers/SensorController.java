package com.yLobanov.sensor.controllers;

import com.yLobanov.sensor.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sensor")
public class SensorController {
    private final SensorService sensorService;
    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }
}
