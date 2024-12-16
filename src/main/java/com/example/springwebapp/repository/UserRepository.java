package com.example.springwebapp.repository;

import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    void deleteByEmail(String email);
    User findUserByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User getByEmailAndPassword(
            @Param("email") String email,
            @Param("password") String password
    );

}
