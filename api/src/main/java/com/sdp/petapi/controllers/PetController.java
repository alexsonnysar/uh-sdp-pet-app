package com.sdp.petapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.petapi.models.Pet;

import com.sdp.petapi.services.PetService;

@RestController
@CrossOrigin
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
  public Pet putPet(@PathVariable String id, @RequestBody Pet pet) {
    return (id == null || pet == null || !id.equals(pet.getId())) ? null : petService.putPet(pet);
  }

  @DeleteMapping("/{id}")
  public Pet deletePet(@PathVariable String id) {
    return petService.deletePet(id);
  }
  
}
