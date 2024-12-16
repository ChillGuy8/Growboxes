package com.example.springwebapp.controller;

import com.example.springwebapp.model.Detail;
import com.example.springwebapp.model.ListOfDetail;
import com.example.springwebapp.model.request.ListOfDetailRequest;
import com.example.springwebapp.service.DetailService;
import com.example.springwebapp.service.ListOfDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/device_detail")
public class ListOfDetailController {
    @Autowired
    private ListOfDetailService service;
    @GetMapping
    public List<ListOfDetail> findAllListOfDetail() {
        return service.findAllListOfDetail();
    }
    @GetMapping("/by_device/{id}")
    public List<ListOfDetail> findByDeviceId(@PathVariable int id) {
        return service.findByDeviceId(id);
    }
    @GetMapping("/{id}")
    public ListOfDetail findById(@PathVariable int id) {
        return service.findById(id);
    }
    @PostMapping("save_device_detail")
    public ListOfDetail saveListOfDetail(@RequestBody ListOfDetailRequest ListOfDetail){
        return service.saveListOfDetail(ListOfDetail);
    }

    @PutMapping("update_device_detail")
    public ListOfDetail updateListOfDetail(@RequestBody ListOfDetailRequest ListOfDetail){
        return service.updateListOfDetail(ListOfDetail);
    }

    @DeleteMapping("delete_device_detail/{id}")
    public void deleteListOfDetail(@PathVariable int id){
        service.deleteListOfDetailById(id);
    }
}
