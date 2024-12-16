package com.example.springwebapp.service.Impl;

import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.User;
import com.example.springwebapp.model.request.DeviceRequest;
import com.example.springwebapp.repository.DeviceRepository;
import com.example.springwebapp.repository.UserRepository;
import com.example.springwebapp.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private final DeviceRepository repository;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public List<Device> findAllDevice() {
        return repository.findAll();
    }

    @Override
    public Device saveDevice(DeviceRequest deviceRequest){
        Optional<User> user = userRepository.findById(deviceRequest.getUserId());

        return repository.save(new Device(
                deviceRequest.getId(),
                deviceRequest.getName(),
                deviceRequest.getSize(),
                deviceRequest.getPrice(),
                deviceRequest.getMode(),
                deviceRequest.getRegcode(),
                user.get()
        ));

    }
    @Override
    public void deleteDevice(int id){
        repository.deleteById(id);

    }

    @Override
    public List<Device> getDevicesByUserId(int id) {
        return repository.findByUserId(id);
    }

    @Override
    public Device updateDevice(DeviceRequest deviceRequest){
        Optional<User> user = userRepository.findById(deviceRequest.getUserId());

        return repository.save(new Device(
                deviceRequest.getId(),
                deviceRequest.getName(),
                deviceRequest.getSize(),
                deviceRequest.getPrice(),
                deviceRequest.getMode(),
                deviceRequest.getRegcode(),
                user.get()
        ));

    }
}
