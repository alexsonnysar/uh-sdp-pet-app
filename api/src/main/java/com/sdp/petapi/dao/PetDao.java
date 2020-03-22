package com.sdp.petapi.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.User;
import com.sdp.petapi.repositories.PetRepository;

@Component
public class PetDao {

  @Autowired
  private PetRepository repository;

  @Autowired
  private UserDao userDao;

  public List<Pet> getAllPets() {
    return repository.findAll();
  }

  public Pet getPetById(String petid){
    if (petid == null) return null;
    
    Optional<Pet> pet = repository.findById(petid);

    return pet.isPresent() ? pet.get() : null;
  }

  public Pet createPet(User user, Pet pet) {
    if(user == null || !user.isEmployee() || pet == null || pet.getId() != null ) return null;

    User userdb = userDao.getUserById(user.getId());
    return (userdb != user) ? null : repository.insert(pet);
  }

  public Pet putPet(User user, Pet pet) {
    if(user == null || !user.isEmployee()) return null;
    
    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;
    
    Pet petdb = getPetById(pet.getId());
    return (petdb == null) ? null : repository.save(pet);
  }

  public Pet deletePet(String petid) {
    if(petid != null) return null;

    Pet pet = getPetById(petid);
    if (pet == null) return null;
    
    repository.delete(pet);
    return pet;
  }

  public Pet putPetByRequest(Pet pet) {
    return repository.save(pet);
  }
  
}
