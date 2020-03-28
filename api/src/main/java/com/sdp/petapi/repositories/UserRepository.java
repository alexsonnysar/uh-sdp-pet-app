package com.sdp.petapi.repositories;

import java.util.Optional;

import com.sdp.petapi.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByEmail(String username);
    
    Boolean existsByEmail(String username);
}

// implements ExampleDao {
// private static List<Example> fakeDB = new ArrayList<>();

// @Override
// public int insertExample(UUID id, Example example) {
// fakeDB.add(new Example(id, example.getMessage()));
// return 0;
// }

// @Override
// public List<Example> selectAllExamples() {
// return fakeDB;
// }
// }
