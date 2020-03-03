package com.sdp.petapi.services;

import java.util.*;
// import org.bson.types.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.petapi.dao.PetDao;
// import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;

@Service
public class PetService {
  
  @Autowired
  private PetDao petDao;
  
  public Pet getUserOnePet(String id) {
    return petDao.getUserOnePet(id);
  }

  public Pet getEmployeeOnePet(String id) {
    return petDao.getEmployeeOnePet(id);
  }
  
  public List<Pet> getUserAllPets(){
    return petDao.getUserAllPets();
  }
  
  public List<Pet> getEmployeeAllPets() {
    return petDao.getEmployeeAllPets();
  }
  
  public Pet addPet(Pet pet) {
    return petDao.addPet(pet);
  }

  public Pet updatePet(Pet pet) {
    return petDao.updatePet(pet);
  }
  
}
