package com.sdp.petapi.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.petapi.dao.PetDao;
// import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;

@Service
public class PetService {
  
  @Autowired
  private PetDao petDao;
  
  public Collection<Pet> getPets() {
    return petDao.getPets();
  }


  public Pet getPet(String id) {
    return petDao.getPet(id).get();
  }

  public List<String> getAllIds(){
    return petDao.getAllIds();
  }
  
  public Pet addPet(Pet pet) {
    return petDao.addPet(pet);
  }

  public String updatePet(Pet pet) {
    return petDao.updatePet(pet);
  }


  public String deletePet(Pet pet) {
    return petDao.deletePet(pet);
  }
}
