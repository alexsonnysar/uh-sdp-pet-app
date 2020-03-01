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

	public Boolean deletePet(String id) {
		return petDao.deletePet(id);
	}

}
