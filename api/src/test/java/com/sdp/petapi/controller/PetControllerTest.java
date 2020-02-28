package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;

import java.util.List;

import com.sdp.petapi.controllers.PetController;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.repositories.PetRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetControllerTest {

  Pet pet = new Pet("123", "TestingPet", "Dog", "M", "15", "small", 150.0, "2012-04-23T18", "cute", new String[] {"A","B","C"}, false);

  @Autowired
  PetController petController;

  @Mock
  PetController mockController;

  @Mock
  Message mockMessage;

  @Autowired
  PetRepository petRepository;

  @Test
  public void get_pets_should_return_all_pets() {
    List<Pet> pets = petController.getAllPets();
    assertEquals(anyList(), pets);
  }






  @Test
  public void pet_should_delete() {
    petRepository.insert(pet);
    Message message = petController.deletePet(pet.getId());
    assertEquals("Deleted Pet", message.getMessage(), "PetController failed delete by id");
    // checking if pet object was actually deleted
    assertEquals(false, petRepository.existsById(pet.getId()), "PetController failed delete by id");
  }



}
