package com.sdp.petapi.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;

@Component
public class PetDao {
	
	@Autowired
	private PetRepository repository;
	
	public Collection<Pet> getPets(){
		return repository.findAll();
	}

	public Pet createPet(Pet pet) {
		return repository.insert(pet);
	}

	public Message deletePet(String id) {
		repository.deleteById(id);
		return new Message("Deleted Pet");
	}

	public Message putPet(Pet pet) {
		repository.save(pet);
		return new Message("Updated Pet");

	}
}
