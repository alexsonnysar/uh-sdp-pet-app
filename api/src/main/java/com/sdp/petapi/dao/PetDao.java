package com.sdp.petapi.dao;

import java.util.*;
// import org.bson.types.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.repositories.PetRepository;
import java.util.Date;

@Component
public class PetDao {

  @Autowired
  private PetRepository repository;

  public List<Pet> getPets() {
    List<Pet> allPets = new ArrayList<Pet>();
    repository.findAll().stream().filter(p -> p.isActive()).forEach(p -> allPets.add(p));
    return allPets;
  }

  public Pet getUserOnePet(String id){
    try {
      Pet p = repository.findById(id).get();
      return (p != null && p.isActive()) ? p : null;
    }
<<<<<<< Updated upstream
    catch (Exception e) {
=======
    else {
>>>>>>> Stashed changes
      return null;
    }
    
  }

<<<<<<< Updated upstream
  public Pet getEmployeeOnePet(String id) {
    try {
      return repository.findById(id).get();
    }
    catch (Exception e) {
      return null;
    }
  }
  
  public List<String> getUserAllIds(){
    List<String> petIds = new ArrayList<String>();
    getPets().stream().filter(p -> p.isActive()).forEach(p -> petIds.add(p.getId()));
    return petIds;
  }

  public List<String> getEmployeeAllIds() {
    List<String> petIds = new ArrayList<String>();
    getPets().forEach(p -> petIds.add(p.getId()));
    return petIds;
  }
  
  public Pet addPet(Pet pet) {
    if(pet == null){
      return pet;
    }
    pet.setDateAdded(new Date());
    pet.setActive(true);
    pet.setAdopted(false);
    return repository.save(pet);
  }

  public String updatePet(Pet pet) {
    if(pet==null) {
      return "Pet does not exist. Try to add new pet instead.";
    }
    repository.save(pet);
    return "Updated Pet";

  }
=======
  public Pet createPet(Pet pet) {
    if(pet == null){
      return pet;
    }
    pet.setDateAdded(new Date());
    pet.setActive(true);
    pet.setAdopted(false);
    return repository.insert(pet);
  }

  public Pet putPet(Pet pet) {
    if(pet == null){
      return pet;
    }
    return repository.save(pet);
  }
  /*
  public Boolean deletePet(String id) {
    repository.deleteById(id);
    // maybe check if delete worked?
    return true;
  }*/
>>>>>>> Stashed changes

  // public String deletePet(Pet pet) {
  //   if(pet==null){
  //     return "Pet does not exist";
  //   }
  //   pet.setActive(false);
  //   repository.save(pet);
  //   return "Deleted Pet";
  // }

}
