package com.sdp.petapi.repositories;

import com.sdp.petapi.models.Requests;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestsRepository extends MongoRepository<Requests, String> {

}
