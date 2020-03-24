package com.sdp.petapi.repositories;

import com.sdp.petapi.models.Requested;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestedRepository extends MongoRepository<Requested, String> {

}