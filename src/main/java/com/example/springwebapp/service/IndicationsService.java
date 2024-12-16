package com.example.springwebapp.service;

import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.Indications;
import com.example.springwebapp.model.request.IndicationsRequest;

import java.util.List;

public interface IndicationsService {
    List<Indications> findAllIndications();
    Indications saveIndication(IndicationsRequest Indications);
    Indications findByID(int id);
    List<Indications> findAllByDevice(int device_id);
}
