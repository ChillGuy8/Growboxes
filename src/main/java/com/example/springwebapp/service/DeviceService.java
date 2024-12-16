package com.example.springwebapp.service;

import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.request.DeviceRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DeviceService {
    List<Device> findAllDevice();
    Device saveDevice(DeviceRequest Device);
    Device updateDevice(DeviceRequest Device);
    void deleteDevice(int id);
    List<Device> getDevicesByUserId(int id);
}
