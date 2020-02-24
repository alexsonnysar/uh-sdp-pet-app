package com.sdp.petapi.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.services.PetService;

@RestController
@RequestMapping("/pet")
public class PetController {
	
	@Autowired
	private PetService petService;
	
	
	
	
	
	
	// make id required
	@DeleteMapping("/{id}")
	public Message deletePet(@PathVariable String id) {
		return petService.deletePet(id);
		
	}
	
	
	
	
	@GetMapping
	public Collection<Pet> getAllPets() {
		return petService.getPets();
	}
	
	@PostMapping
	public Pet postPet(@RequestBody Pet pet) {
		return petService.createPet(pet);
	}
	
	@PutMapping("/{id}")
	public Message putPet(@PathVariable String id, @RequestBody Pet pet) {
		pet.setId(id);
		return petService.putPet(pet);
	}
}
