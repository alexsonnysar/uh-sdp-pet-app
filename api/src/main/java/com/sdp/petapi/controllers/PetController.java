package com.sdp.petapi.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.services.PetService;

@RestController
public class PetController {

  @Autowired
  private PetService petService;

  // @GetMapping
  @RequestMapping("/user/pet")
  public List<String> getUserAllPets() {
    return petService.getUserAllIds();
  }

  @RequestMapping("/employee/pet")
  public List<String> getEmployeeAllPets() {
    return petService.getEmployeeAllIds();
  }

  @GetMapping("/user/pet/{id}")
  public Pet getUserOnePet(@PathVariable String id) {
    return petService.getUserOnePet(id);
  }

  @GetMapping("/employee/pet/{id}")
  public Pet getEmployeeOnePet(@PathVariable String id) {
    return petService.getEmployeeOnePet(id);
  }

  @PostMapping("/employee/pet")
  public Pet postPet(@RequestBody Pet pet) {
    return petService.addPet(pet);
  }

  @PutMapping("/employee/pet/{id}")
  public String updatePet(@PathVariable String id, @RequestBody Pet pet) {
    return petService.updatePet(pet);
  }

  // @DeleteMapping("/employee/pet/{id}")
  // public String deletePet(@PathVariable String id) {
  //   return petService.deletePet(getEmployeeOnePet(id));
  // }
}
