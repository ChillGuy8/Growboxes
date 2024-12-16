package com.example.springwebapp.controller;

import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.User;
import com.example.springwebapp.model.request.DeviceRequest;
import com.example.springwebapp.model.request.ListOfDetailRequest;
import com.example.springwebapp.service.DeviceService;
import com.example.springwebapp.service.ListOfDetailService;
import com.example.springwebapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {
    @Autowired
    private DeviceService service;
    @Autowired
    private ListOfDetailService listOfDetailService;
    @GetMapping
    public List<Device> findAllDevice() {
        return service.findAllDevice();
    }
    @PostMapping("save_device")
    public Device saveDevice(@RequestBody DeviceRequest device){
        return service.saveDevice(device);
    }
    @PostMapping("save_random_device/{user_id}/{regcode}")
    public Device saveDevice(
            @PathVariable("regcode") String regcode,
            @PathVariable("user_id") int userId
    ){
        Random random = new Random();

        DeviceRequest device = new DeviceRequest();
        if (random.nextInt(0, 2) == 0) {
            device = new DeviceRequest(
                    0,
                    "гроубокс-1",
                    "200*200*200",
                    5000,
                    "авто",
                    regcode,
                    userId
            );
        } else {
            device = new DeviceRequest(
                    0,
                    "гроубокс-2",
                    "250*200*250",
                    9000,
                    "авто",
                    regcode,
                    userId
            );
        }

        Device savedDevice = service.saveDevice(device);

        if (savedDevice.getName().equals("гроубокс-1")) {
            listOfDetailService.saveListOfDetail(
                    new ListOfDetailRequest(
                            0,
                            savedDevice.getId(),
                            1,
                            1
                    )
            );
            listOfDetailService.saveListOfDetail(
                    new ListOfDetailRequest(
                            0,
                            savedDevice.getId(),
                            2,
                            1
                    )
            );
            listOfDetailService.saveListOfDetail(
                    new ListOfDetailRequest(
                            0,
                            savedDevice.getId(),
                            3,
                            1
                    )
            );
        } else {
            listOfDetailService.saveListOfDetail(
                    new ListOfDetailRequest(
                            0,
                            savedDevice.getId(),
                            1,
                            1
                    )
            );
            listOfDetailService.saveListOfDetail(
                    new ListOfDetailRequest(
                            0,
                            savedDevice.getId(),
                            2,
                            2
                    )
            );
            listOfDetailService.saveListOfDetail(
                    new ListOfDetailRequest(
                            0,
                            savedDevice.getId(),
                            3,
                            2
                    )
            );
        }

        return savedDevice;
    }

    @PutMapping("update_device")
    public Device updateDevice(@RequestBody DeviceRequest device){
        return service.updateDevice(device);
    }

    @DeleteMapping("delete_device/{id}")
    public void  deleteUser(@PathVariable int id){
        service.deleteDevice(id);
    }

    @GetMapping("/devices/{userId}")
    public ResponseEntity<List<Device>> getDevicesByUserId(@PathVariable int userId) {
        List<Device> devices = service.getDevicesByUserId(userId);
        return ResponseEntity.ok(devices);
    }

}
