package com.example.springwebapp.repository;

import com.example.springwebapp.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    void deleteById(int id);

    @Query(value = "select * from device where user_id = :id", nativeQuery = true)
    List<Device> findByUserId(@Param("id") int id);
}
