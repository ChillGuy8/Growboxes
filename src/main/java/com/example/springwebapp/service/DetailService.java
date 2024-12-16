package com.example.springwebapp.service;

import com.example.springwebapp.model.Detail;

import java.util.List;

public interface DetailService {
    List<Detail> findAllDetail();
    Detail findById(int id);
    Detail saveDetail(Detail Detail);
    Detail updateDetail(Detail Detail);
    void deleteDetail(int id);
}
