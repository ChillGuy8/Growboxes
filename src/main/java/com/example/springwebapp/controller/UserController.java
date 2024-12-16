package com.example.springwebapp.controller;

import com.example.springwebapp.model.LoginRequestBody;
import com.example.springwebapp.model.User;
import com.example.springwebapp.service.Impl.UserServiceImpl;
import com.example.springwebapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "https://growboxes.onrender.com")
public class UserController {
    @Autowired
    private UserService service;
    @GetMapping
    public List<User> findAllUsers() {
        return service.findAllUsers();
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        // Check if the user already exists
        if (service.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with the same email already exists.");
        }

        // Save the new user to the database
        service.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequestBody loginRequest) {
        User user = service.login(loginRequest);

        if (user != null) {
            // Simulate storing user session (In a real app, you'd use session management)
            return ResponseEntity.ok(user); // Return the user object
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

//    @PostMapping("save_user")
//    public User saveUser(@RequestBody User user){
//        return  service.saveUser(user);
//    }
    @GetMapping("/email/{email}")
    public User findByEmail(@PathVariable String email){
        return service.findByEmail(email);
    }

//    @PutMapping("update_user")
//    public User updateUser(@RequestBody User user){
//        return service.updateUser(user);
//    }
//
//    @DeleteMapping("delete_user/{email}")
//    public void  deleteUser(@PathVariable String email){
//        service.deleteUser(email);
//    }
    @GetMapping("/user/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = service.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
