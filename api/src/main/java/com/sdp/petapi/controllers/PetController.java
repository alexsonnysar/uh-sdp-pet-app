package com.sdp.petapi.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.services.PetService;

@RestController
@RequestMapping("/pet")
public class PetController {

  @Autowired
  private PetService petService;

  // @GetMapping
  public List<String> getAllPets() {
    return petService.getAllIds();
  }

  @GetMapping("/{id}")
  public Pet getOnePet(@PathVariable String id) {
    return petService.getPet(id);
  }

  @PostMapping
  public Pet postPet(@RequestBody Pet pet) {
    return petService.addPet(pet);
  }

  @PutMapping("/{id}")
  public String updatePet(@PathVariable String id, @RequestBody Pet pet) {
    return petService.updatePet(pet);
  }

  @DeleteMapping("/{id}")
  public String deletePet(@PathVariable String id) {
    return petService.deletePet(getOnePet(id));
  }
}
