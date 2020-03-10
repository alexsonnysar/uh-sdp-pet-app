package com.sdp.petapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/pet")
public class PetController {

  @Autowired
  private PetService petService;

  @GetMapping
  @CrossOrigin
  public List<Pet> getAllPets() {
    return petService.getEmployeeAllPets();
  }

  @GetMapping("/{id}")
  public Pet getPetById(@PathVariable String id) {
    return petService.getEmployeePetById(id);
  }

  @PostMapping
  public Pet createPet(@RequestBody Pet pet) {
    return petService.createPet(pet);
  }

  @PutMapping("/{id}")
  public Pet putPet(@PathVariable String id, @RequestBody Pet pet) {
    return (id == null | pet == null || pet.getId() != id) ? null : petService.putPet(pet);
  }
}
