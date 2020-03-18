package com.sdp.petapi.dao;

import java.util.*;
import java.util.stream.Collectors;

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

  @Autowired
  private RequestsDao reqDao;

  public List<Pet> getAllPets(User user) {
    if (user == null) return null;

    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;

    return (user.isEmployee()) ? repository.findAll() : 
      repository.findAll().stream().filter(p -> p.isActive()).collect(Collectors.toList());
  }

  public Pet getPetById(User user, String petid){
    if (petid == null) return null;
    
    Optional<Pet> pet = repository.findById(petid);

    if (!pet.isPresent()) return null;

    Boolean userCond = (user.isEmployee() || pet.get().isActive()
      || reqDao.getAllRequests(user)
        .stream()
        .anyMatch(r -> r.getUserid() == user.getId() && r.getPetid() == petid)
    );

    return userCond ? pet.get() : null;
  }

  public Pet createPet(User user, Pet pet) {
    if(user == null || !user.isEmployee() || pet == null || pet.getId() != null ) return null;

    User userdb = userDao.getUserById(user.getId());
    return (userdb != user) ? null : repository.insert(pet);
  }

  public Pet putPet(User user, Pet pet) {
    if(user == null || !user.isEmployee() || pet == null) return null;
    
    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;
    
    Pet petdb = getPetById(user, pet.getId());
    return (petdb == null) ? null : repository.save(pet);
  }

  public Pet deletePet(User user, String petid) {
    if(user == null || petid != null || !user.isEmployee()) return null;

    User userdb = userDao.getUserById(user.getId());
    if (userdb != user) return null;

    Pet pet = getPetById(user, petid);
    if (pet == null) return null;
    
    repository.delete(pet);
    return pet;
  }

  public Pet putPetByRequest(Pet pet) {
    return repository.save(pet);
  }
  
}
