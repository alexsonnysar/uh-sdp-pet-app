package com.sdp.petapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.petapi.dao.PetDao;

import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.User;

@Service
public class PetService {

  @Autowired
  private PetDao petDao;

  public List<Pet> getAllPets(User user) {
    return petDao.getAllPets(user);
  }
  
  public Pet getPetById(User user, String id) {
    return petDao.getPetById(user, id);
  }

  public Pet createPet(User user, Pet pet) {
    return petDao.createPet(user, pet);
  }

  public Pet putPet(User user, Pet pet) {
    return petDao.putPet(user, pet);
  }

  public Pet deletePet(User user, String petid) {
    return petDao.deletePet(user, petid);
  }
}
