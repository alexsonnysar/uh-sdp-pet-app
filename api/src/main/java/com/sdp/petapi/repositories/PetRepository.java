package com.sdp.petapi.repositories;

import com.sdp.petapi.models.Pet;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PetRepository extends MongoRepository<Pet, String> {
    
}
