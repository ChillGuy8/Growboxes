package com.example.springwebapp.service.Impl;

import com.example.springwebapp.model.Device;
import com.example.springwebapp.model.LoginRequestBody;
import com.example.springwebapp.model.User;
import com.example.springwebapp.repository.UserRepository;
import com.example.springwebapp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository repository;

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }
    @Transactional
    @Override
    public User saveUser(User user){
        return repository.save(user);

    }
    @Override
    public User findByEmail(String email){
        return repository.findUserByEmail(email);

    }
    @Transactional
    @Override
    public User updateUser(User user){
        return repository.save(user);

    }
    @Transactional
    @Override
    public void deleteUser(String email){
         repository.deleteByEmail(email);

    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        return repository.getByEmailAndPassword(email, password);
    }

    @Override
    public User login(LoginRequestBody loginRequest) {
        return repository.getByEmailAndPassword(
                loginRequest.email,
                loginRequest.password
        );
    }

    public boolean existsByEmail(String email) {
        return repository.findUserByEmail(email) != null; // Assume findByEmail returns null if not found
    }

}
