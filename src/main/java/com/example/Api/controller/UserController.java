package com.example.Api.controller;

import com.example.Api.model.User;
import com.example.Api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(401).body("Invalid credentials!");
    }

 // Update user
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        Optional<User> user = userService.updateUser(id, updatedUser);

        // Check if user is present and return appropriate response
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);

        // Check if user is present and return appropriate response
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }



    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.ok("User deleted successfully") : ResponseEntity.status(404).body("User not found");
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
