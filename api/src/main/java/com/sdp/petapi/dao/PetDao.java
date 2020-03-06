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

  public List<Pet> getUserAllPets(){
    List<Pet> pets = new ArrayList<Pet>();
    repository.findAll().stream().filter(p -> p.isActive()).forEach(p -> pets.add(p));;
    return pets;
  }

  public List<Pet> getEmployeeAllPets(){
    return repository.findAll();
  }

  public Pet getUserPetById(String pet_id){
    if (pet_id == null) return null;
    Optional<Pet> pet = repository.findById(pet_id);
    return (pet.isPresent() && pet.get().isActive()) ? pet.get() : null;
  }
  
  public Pet getEmployeePetById(String pet_id){
    if (pet_id == null) return null;
    Optional<Pet> pet = repository.findById(pet_id);
    return pet.isPresent() ? pet.get() : null;
  }

  public Pet createPet(Pet pet) {
    if(pet == null) return pet;

    pet.setDateAdded(new Date());
    pet.setActive(true);
    pet.setAdopted(false);
    return repository.insert(pet);
  }

  public Pet putPet(Pet pet) {
    if(pet == null) return pet;
    
    return repository.save(pet);
  }
}
