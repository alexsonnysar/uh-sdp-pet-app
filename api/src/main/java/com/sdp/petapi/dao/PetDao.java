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

  public Pet getUserOnePet(String id){
    if (id == null) return null;
    Optional<Pet> pet = repository.findById(id);
    return (pet.isPresent() && pet.get().isActive()) ? pet.get() : null;
  }

  public Pet getEmployeeOnePet(String id) {
    if (id == null) return null;
    Optional<Pet> pet = repository.findById(id);
    return pet.isPresent() ? pet.get() : null;
  }
  
  public List<Pet> getUserAllPets(){
    List<Pet> pets = new ArrayList<Pet>();
    repository.findAll().stream().filter(p -> p.isActive()).forEach(p -> pets.add(p));;
    return pets;
  }

  public List<Pet> getEmployeeAllPets(){
    return repository.findAll();
  }
  
  public Pet addPet(Pet pet) {
    if(pet == null){
      return pet;
    }
    pet.setDateAdded(new Date());
    pet.setActive(true);
    pet.setAdopted(false);
    return repository.insert(pet);
  }

  public Pet updatePet(Pet pet) {
    if(pet==null) {
      return null;
    }
    return repository.save(pet);

  }
  
}
