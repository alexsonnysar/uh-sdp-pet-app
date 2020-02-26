package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyCollection;

import java.util.Collection;

import com.sdp.petapi.controllers.PetController;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetControllerTest {

  @Autowired
  PetController petController;

  @Mock
  PetController mockController;

  @Mock
  Message mockMessage;

  @Test
  public void get_pets_should_return_all_pets() {
    Collection<Pet> pets = petController.getAllPets();
    assertEquals(anyCollection(), pets);
  }

  @Test
  public void pet_should_delete() {
    Pet pet = new Pet();
    pet.setId("001");
    mockMessage.setMessage("Deleted Pet");
    Message message = petController.deletePet(pet.getId());
    assertEquals("Deleted Pet" ,message.getMessage());
   }

}
