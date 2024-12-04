package com.example.Api.service;

import com.example.Api.model.User;
import com.example.Api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Method to generate a random 6-digit ID
    private String generateRandomId() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // Generates a 6-digit ID
    }

    // Register a new user with a randomly generated ID
    public User registerUser(User user) {
        user.setId(generateRandomId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Login User
    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    // Get User by ID
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            if (updatedUser.getUsername() != null) {
                user.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            if (updatedUser.getFullName() != null) {
                user.setFullName(updatedUser.getFullName());
            }
            return userRepository.save(user);
        });
    }


    // Delete User
    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get All Users
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
