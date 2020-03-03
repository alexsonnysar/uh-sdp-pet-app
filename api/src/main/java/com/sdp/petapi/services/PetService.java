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
<<<<<<< Updated upstream
  
  @Autowired
  private PetDao petDao;
  
  public Collection<Pet> getPets() {
    return petDao.getPets();
  }


  public Pet getUserOnePet(String id) {
    return petDao.getUserOnePet(id);
  }

  public Pet getEmployeeOnePet(String id) {
    return petDao.getEmployeeOnePet(id);
  }
  
  public List<String> getUserAllIds(){
    return petDao.getUserAllIds();
  }
  
  public List<String> getEmployeeAllIds() {
    return petDao.getEmployeeAllIds();
  }
  
  public Pet addPet(Pet pet) {
    return petDao.addPet(pet);
  }

  public String updatePet(Pet pet) {
    return petDao.updatePet(pet);
  }


  // public String deletePet(Pet pet) {
  //   return petDao.deletePet(pet);
  // }
=======

	@Autowired
	private PetDao petDao;

	public List<Pet> getAllPets() {
		return petDao.getAllPets();
	}

	public Pet getPetById(String id) {
		return petDao.getPetById(id);
	}

	public Pet createPet(Pet pet) {
		return petDao.createPet(pet);
	}

	public Pet putPet(Pet pet) {
		return petDao.putPet(pet);
	}
	/*
	public Boolean deletePet(String id) {
		return petDao.deletePet(id);
	}*/
>>>>>>> Stashed changes

}
