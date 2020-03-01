package com.sdp.petapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.services.PetService;

@RestController
@RequestMapping("/pet")
public class PetController {

  @Autowired
  private PetService petService;
  @GetMapping
  public List<Pet> getAllPets() {
    return petService.getAllPets();
  }

  @GetMapping("/{id}")
  public Pet getPetById(@PathVariable String id) {
    return petService.getPetById(id);
  }

  @PostMapping
  public Pet createPet(@RequestBody Pet pet) {
    return petService.createPet(pet);
  }

  @PutMapping("/{id}")
  public Message putPet(@PathVariable String id, @RequestBody Pet pet) {
    pet.setId(id);
    Pet returnedPet = petService.putPet(pet);
    if (returnedPet != null) {
      return new Message("Updated Pet");
    } else {
      return new Message("Couldn't update pet");
    }
  }

  @DeleteMapping("/{id}")
  public Message deletePet(@PathVariable String id) {
    if (petService.deletePet(id)) {
      return new Message("Deleted Pet");
    } else {
      return new Message("Couldn't delete pet");
    }
  }

}
