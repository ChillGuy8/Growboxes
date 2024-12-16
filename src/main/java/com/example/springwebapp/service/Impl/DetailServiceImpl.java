package com.example.springwebapp.service.Impl;

import com.example.springwebapp.model.Detail;
import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.User;
import com.example.springwebapp.repository.DetailRepository;
import com.example.springwebapp.repository.DeviceRepository;
import com.example.springwebapp.service.DetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DetailServiceImpl implements DetailService {
    private final DetailRepository repository;
    @Override
    public List<Detail> findAllDetail() {
        return repository.findAll();
    }
    @Override
    public Detail findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public Detail saveDetail(Detail Detail){
        return repository.save(Detail);

    }
    @Override
    public void deleteDetail(int id){
        repository.deleteById(id);

    }
    @Override
    public Detail updateDetail(Detail Detail){
        return repository.save(Detail);

    }
}
