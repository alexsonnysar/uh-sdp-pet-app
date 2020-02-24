package com.sdp.petapi.dao;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.sdp.petapi.models.Pet;

public interface PetRepository extends MongoRepository<Pet, String>{
	
}
