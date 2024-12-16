package com.example.springwebapp.repository;

import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.ListOfDetail;
import com.example.springwebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListOfDetailRepository extends JpaRepository<ListOfDetail, Integer> {
    void deleteById(int id);

    @Query(value = "select * from list_of_detail where device_id = :id", nativeQuery = true)
    List<ListOfDetail> findByDeviceId(@Param("id") int id);
}
