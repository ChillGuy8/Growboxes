package com.example.springwebapp.service;

import com.example.springwebapp.model.ListOfDetail;
import com.example.springwebapp.model.request.ListOfDetailRequest;

import java.util.List;

public interface ListOfDetailService {
    List<ListOfDetail> findAllListOfDetail();
    List<ListOfDetail> findByDeviceId(int id);
    ListOfDetail findById(int id);
    ListOfDetail saveListOfDetail(ListOfDetailRequest ListOfDetail);
    ListOfDetail updateListOfDetail(ListOfDetailRequest ListOfDetail);
    void deleteListOfDetailById(int id);
}
