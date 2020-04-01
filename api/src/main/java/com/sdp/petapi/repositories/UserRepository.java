package com.sdp.petapi.repositories;

import java.util.Optional;

import com.sdp.petapi.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByEmail(String username);
    
    Boolean existsByEmail(String username);
}
