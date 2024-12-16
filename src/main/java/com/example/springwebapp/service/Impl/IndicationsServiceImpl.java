package com.example.springwebapp.service.Impl;

import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.Indications;
import com.example.springwebapp.model.request.IndicationsRequest;
import com.example.springwebapp.repository.DeviceRepository;
import com.example.springwebapp.repository.IndicationsRepository;
import com.example.springwebapp.service.IndicationsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IndicationsServiceImpl implements IndicationsService {


    @Autowired
    private final IndicationsRepository repository;

    @Autowired
    private final DeviceRepository deviceRepository;

    @Override
    public List<Indications> findAllIndications() {
        return repository.findAll();
    }

    @Override
    public Indications saveIndication(IndicationsRequest indicationsRequest){
        Optional<Device> device = deviceRepository.findById(indicationsRequest.getDeviceId());

        return repository.save(new Indications(
                indicationsRequest.getId(),
                indicationsRequest.getName(),
                indicationsRequest.getValue(),
                indicationsRequest.getValueName(),
                device.get()
        ));
    }


    @Override
    public Indications findByID(int id){
        return repository.findById(id);

    }
    @Override
    public List<Indications> findAllByDevice(int device_id){
        return repository.findAllByDevice(device_id);

    }

}
