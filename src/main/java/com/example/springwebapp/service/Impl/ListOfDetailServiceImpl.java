package com.example.springwebapp.service.Impl;

import com.example.springwebapp.model.Detail;
import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.ListOfDetail;
import com.example.springwebapp.model.request.ListOfDetailRequest;
import com.example.springwebapp.repository.DetailRepository;
import com.example.springwebapp.repository.DeviceRepository;
import com.example.springwebapp.repository.ListOfDetailRepository;
import com.example.springwebapp.service.ListOfDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListOfDetailServiceImpl implements ListOfDetailService {

    @Autowired
    private final ListOfDetailRepository repository;

    @Autowired
    private final DetailRepository detailRepository;
    @Autowired
    private final DeviceRepository deviceRepository;

    @Override
    public List<ListOfDetail> findAllListOfDetail() {
        return repository.findAll();
    }

    @Override
    public List<ListOfDetail> findByDeviceId(int id) {
        return repository.findByDeviceId(id);
    }

    @Override
    public ListOfDetail findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public ListOfDetail saveListOfDetail(ListOfDetailRequest listOfDetailRequest){
        Optional<Device> device = deviceRepository.findById(listOfDetailRequest.getDeviceId());
        Optional<Detail> detail = detailRepository.findById(listOfDetailRequest.getDetailId());

        return repository.save(new ListOfDetail(
                listOfDetailRequest.getId(),
                device.get(),
                detail.get(),
                listOfDetailRequest.getCount()
        ));
    }

    @Override
    public ListOfDetail updateListOfDetail(ListOfDetailRequest listOfDetailRequest){
        Optional<Device> device = deviceRepository.findById(listOfDetailRequest.getDeviceId());
        Optional<Detail> detail = detailRepository.findById(listOfDetailRequest.getDetailId());

        return repository.save(new ListOfDetail(
                listOfDetailRequest.getId(),
                device.get(),
                detail.get(),
                listOfDetailRequest.getCount()
        ));

    }
    @Override
    public void deleteListOfDetailById(int id){
        repository.deleteById(id);

    }
}
