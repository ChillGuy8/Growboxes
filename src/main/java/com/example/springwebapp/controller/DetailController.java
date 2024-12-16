package com.example.springwebapp.controller;

import com.example.springwebapp.model.Detail;
import com.example.springwebapp.service.DetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/detail")
@CrossOrigin(origins = "https://growboxes.onrender.com")
public class DetailController {
    @Autowired
    private DetailService service;
    @GetMapping
    public List<Detail> findAllDetail() {
        return service.findAllDetail();
    }
    @GetMapping("/{id}")
    public Detail findByIdDetail(@PathVariable int id) {
        return service.findById(id);
    }
    @PostMapping("save_detail")
    public Detail saveDetail(@RequestBody Detail detail){
        return service.saveDetail(detail);
    }

    @PutMapping("save_detail")
    public Detail updateDetail(@RequestBody Detail detail){
        return service.updateDetail(detail);
    }

    @DeleteMapping("delete_detail/{id}")
    public void deleteDetail(@PathVariable int id){
        service.deleteDetail(id);
    }

}
