package com.sdp.petapi.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.petapi.dao.PetDao;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;

@Service
public class PetService {
	
	@Autowired
	private PetDao petDao;
	
	public Collection<Pet> getPets() {
		return petDao.getPets();
	}


	public Pet getPet(UUID id) {
		return petDao.getPet(id);
	}


	public Pet createPet(Pet pet) {
		return petDao.createPet(pet);
	}

	public Message deletePet(String id) {
		return petDao.deletePet(id);
	}

	public Message putPet(Pet pet) {
		return petDao.putPet(pet);
	}
}
