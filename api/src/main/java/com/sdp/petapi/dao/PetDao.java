package com.sdp.petapi.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.repositories.PetRepository;

@Component
public class PetDao {

  @Autowired
  private PetRepository repository;

  public Collection<Pet> getPets() {
    return repository.findAll();
  }

  public Pet getPet(UUID id){
    return getPets().stream().filter(p -> p.getId().equals(id)).findFirst().get();
  }

  public Pet createPet(Pet pet) {
    return repository.insert(pet);
  }

  public Message putPet(Pet pet) {
    repository.save(pet);
    return new Message("Updated Pet");

  }
}
