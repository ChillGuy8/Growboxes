package com.example.springwebapp.service;

import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.LoginRequestBody;
import com.example.springwebapp.model.User;
import jakarta.transaction.Transactional;

import java.util.List;


public interface UserService {
     List<User> findAllUsers();
     @Transactional
     User saveUser(User user);

     User findByEmail(String email);
     User updateUser(User user);
     @Transactional
     void deleteUser(String email);

     User login(LoginRequestBody loginRequest);
     User getByEmailAndPassword(String email,String password);
     boolean existsByEmail(String email);
}
