package com.example.springwebapp.repository;

import com.example.springwebapp.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<Detail, Integer> {
    void deleteById(int id);
}
