package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.controllers.PetController;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetControllerTest {

  @Autowired
  PetController petController;

  @Test
  public void get_pets_should_return_all_pets() throws Exception {
    List<Pet> pets = petController.getAllPets();
    assertEquals(anyList(), pets);
  }

  @Test
  public void pet_should_delete_pet_by_id() throws Exception {
    Pet pet = new Pet();
    pet.setId("001");
    Message message = petController.deletePet(pet.getId());
    assertEquals(any(Message.class), message.getMessage());
  }

  @Test
  public void pet_should_create_pet() throws Exception {
    ObjectMapper om = new ObjectMapper();
    Pet pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    Pet actual_pet = petController.postPet(pet);
    assertEquals(any(Pet.class), actual_pet);
  }

}
