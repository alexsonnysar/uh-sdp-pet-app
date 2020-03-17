package com.sdp.petapi.repositories;

import com.sdp.petapi.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    
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
