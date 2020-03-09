package com.sdp.petapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.petapi.dao.PetDao;
import com.sdp.petapi.models.Pet;

@Service
public class PetService {

	@Autowired
	private PetDao petDao;

	public List<Pet> getUserAllPets() {
		return petDao.getUserAllPets();
	}

	public List<Pet> getEmployeeAllPets() {
		return petDao.getEmployeeAllPets();
	}
	
	public Pet getUserPetById(String id) {
		return petDao.getUserPetById(id);
	}

	public Pet getEmployeePetById(String id) {
		return petDao.getEmployeePetById(id);
	}

	public Pet createPet(Pet pet) {
		return petDao.createPet(pet);
	}

	public Pet putPet(Pet pet) {
		return petDao.putPet(pet);
	}

}
