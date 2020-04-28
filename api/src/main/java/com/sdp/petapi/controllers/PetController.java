package com.sdp.petapi.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.sdp.petapi.models.RequestInformation;
import com.sdp.petapi.services.PetService;

@RestController
@CrossOrigin
@RequestMapping("/pet")
public class PetController {

  @Autowired
  transient PetService petService;

  @Autowired
  transient RequestsController reqController;

  @Autowired
  transient UserController userController;

  @GetMapping
  public List<Pet> getAllPets() {
    if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Employee"))) {
      return petService.getAllPets();
    }
    else {
      return petService.getAllPets().stream().filter(p -> p.isActive()).collect(Collectors.toList());
    }
  }

  @GetMapping("/{id}")
  public Pet getPetById(@PathVariable String id) {
    return petService.getPetById(id);
  }

  @PostMapping
  @PreAuthorize("hasRole('Employee')")
  public Pet createPet(@RequestBody Pet pet) {
    return petService.createPet(pet);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('Employee')")
  public Pet putPet(@PathVariable String id, @RequestBody Pet pet) {
    return (id != null && pet != null && id.equals(pet.getId())) ? petService.putPet(pet) : null;
  }

  @DeleteMapping("/{id}")
  public Pet deletePet(@PathVariable String id) {
    return petService.deletePet(id);
  }
  
}
