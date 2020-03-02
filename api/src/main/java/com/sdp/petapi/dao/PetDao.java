package com.sdp.petapi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.repositories.PetRepository;

@Component
public class PetDao {

  @Autowired
  private PetRepository repository;

  public List<Pet> getAllPets() {
    return repository.findAll();
  }

  public Pet getPetById(String id) {
    Optional<Pet> pet = repository.findById(id);
    if (pet.isPresent()) {
      return pet.get();
    }
    else {
      return new Pet();
    }
  }

  public Pet createPet(Pet pet) {
    return repository.insert(pet);
  }

  public Message putPet(Pet pet) {
    repository.save(pet);
    return new Message("Updated Pet");
  }

  public Message deletePet(String id) {
    repository.deleteById(id);
    return new Message("Deleted Pet");
  }

}
