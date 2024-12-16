package com.example.springwebapp.repository;

import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.Indications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndicationsRepository extends JpaRepository<Indications, Integer> {
    @Query(value = "SELECT * FROM indications WHERE device_id = :id",nativeQuery = true)
    List<Indications> findAllByDevice(@Param("id") int id);
    Indications findById(int id);
}
