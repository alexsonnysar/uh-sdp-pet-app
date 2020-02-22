package com.sdp.petapi.repositories;

import com.sdp.petapi.models.Pet;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("pet")
public
interface PetRepository extends ReactiveCrudRepository<Pet, String> {

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
