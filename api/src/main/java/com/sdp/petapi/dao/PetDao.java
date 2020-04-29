package com.sdp.petapi.dao;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.Requests;
import com.sdp.petapi.repositories.PetRepository;
import com.sdp.petapi.repositories.RequestsRepository;

@Component
public class PetDao {

  @Autowired
  transient PetRepository repository;

  @Autowired
  transient RequestsRepository reqRepo;

  public List<Pet> getAllPets() {
    return repository.findAll();
  }

  public Pet getPetById(String petid){
    if (petid == null) return null;
    
    Optional<Pet> pet = repository.findById(petid);

    return pet.isPresent() ? pet.get() : null;
  }

  public Pet createPet(Pet pet) {
    if (pet == null || pet.getId() != null ) return null;
    
    pet.capitalizeName();
    return repository.insert(pet);
  }

  public Pet putPet(Pet pet) {
    if (pet == null || getPetById(pet.getId()) == null) return null;
    
    pet.capitalizeName();
    
    if (!pet.isActive()) {
      List<Requests> cancelReqs = reqRepo.findAll()
        .stream()
        .filter(r -> r.getPetid().equals(pet.getId()) && r.getStatus().equals("PENDING"))
        .collect(Collectors.toList());
      
      cancelReqs.forEach(r -> r.setStatus("CANCELED"));
      reqRepo.saveAll(cancelReqs);
    }
    
    return repository.save(pet);
  }

  public Pet deletePet(String petid) {
    if (petid == null) return null;

    Pet pet = getPetById(petid);
    if (pet == null) return null;
    
    repository.delete(pet);
    return pet;
  }

  public Pet putPetByRequest(Pet pet) {
    pet.capitalizeName();
    return repository.save(pet);
  }
  
}
