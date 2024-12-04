package com.example.Api.repository;

import com.example.Api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    // Method to find a user by username
    Optional<User> findByUsername(String username);
}
