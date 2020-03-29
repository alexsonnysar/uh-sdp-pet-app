package com.sdp.petapi.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.petapi.models.Pet;
import com.sdp.petapi.repositories.PetRepository;

@Component
public class PetDao {

  @Autowired
  private PetRepository repository;

  public List<Pet> getAllPets() {
    return repository.findAll();
  }

  public Pet getPetById(String petid){
    if (petid == null) return null;
    
    Optional<Pet> pet = repository.findById(petid);

    return pet.isPresent() ? pet.get() : null;
  }

  public Pet createPet(Pet pet) {
    return (pet == null || pet.getId() != null ) ? null : repository.insert(pet);
  }

  public Pet putPet(Pet pet) {
    return (pet == null || getPetById(pet.getId()) == null) ? null : repository.save(pet);
  }

  public Pet deletePet(String petid) {
    if (petid == null) return null;

    Pet pet = getPetById(petid);
    if (pet == null) return null;
    
    repository.delete(pet);
    return pet;
  }

  public Pet putPetByRequest(Pet pet) {
    return repository.save(pet);
  }
  
}
