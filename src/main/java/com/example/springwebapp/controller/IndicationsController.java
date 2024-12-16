package com.example.springwebapp.controller;

import com.example.springwebapp.model.Detail;
import com.example.springwebapp.model.Indications;
import com.example.springwebapp.model.request.IndicationsRequest;
import com.example.springwebapp.service.DetailService;
import com.example.springwebapp.service.IndicationsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/indications")
public class IndicationsController {

    @Autowired
    private IndicationsService service;
    @GetMapping
    public List<Indications> findAllIndications() {
        return service.findAllIndications();
    }
    @PostMapping("save_indications")
    public Indications saveIndication(@RequestBody IndicationsRequest indications){
        return service.saveIndication(indications);
    }

    @PostMapping("/devices/take-measurements/{deviceId}")
    public ResponseEntity<String> takeMeasurements(@PathVariable int deviceId) {
        // Генерация случайных данных для температуры и влажности
        Random random = new Random();

        // Температура от 20 до 50
        float temperature = 20 + (50 - 20) * random.nextFloat();
        // Влажность от 30 до 80
        float humidity = 30 + (80 - 30) * random.nextFloat();

        // Создаем объекты Indication для температуры и влажности
        IndicationsRequest temperatureIndication = new IndicationsRequest(0, "Температура", temperature, "Градусов", deviceId);
        IndicationsRequest humidityIndication = new IndicationsRequest(0, "Влажность",  humidity,"Процентов", deviceId);

        // Добавляем измерения в базу данных
        service.saveIndication(temperatureIndication);
        service.saveIndication(humidityIndication);

        return ResponseEntity.ok("Измерения успешно сняты.");
    }

    @GetMapping("findAllByDevice_indications/{id}")
    public List<Indications> findAllById(@PathVariable int id){
        return service.findAllByDevice(id);
    }
    @GetMapping("find_indication_by_id/{id}")
    public Indications findById(@PathVariable int id){
        return service.findByID(id);
    }

}
