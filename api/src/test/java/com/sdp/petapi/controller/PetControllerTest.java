package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.controllers.PetController;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.services.PetService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetControllerTest {

  Pet pet;

  @Mock
  PetService petService;

  // makes a pet controller whose petService is the mock above
  @InjectMocks
  PetController petController;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_all_pets() {
    // Since the petService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petService.getAllPets()).thenReturn(Collections.singletonList(pet));
    List<Pet> list = petController.getAllPets();
    assertEquals(Collections.singletonList(pet), list);
  }

  @Test
  public void get_pet_by_id() {
    when(petService.getPetById(pet.getId())).thenReturn(pet);
    Pet returnPet = petController.getPetById(pet.getId());
    assertEquals(pet, returnPet);
  }

  @Test
  public void create_pet() {
    when(petService.createPet(pet)).thenReturn(pet);
    Pet returnPet = petController.createPet(pet);
    assertEquals(pet, returnPet);
  }
  @Test
  public void put_pet() {
    when(petService.putPet(pet)).thenReturn(pet);
    Message returnMessage = petController.putPet(pet.getId(), pet);
    assertEquals("Updated Pet", returnMessage.getMessage());
  }
  @Test
  public void delete_pet() {
    when(petService.deletePet(pet.getId())).thenReturn(true);
    Message returnMessage = petController.deletePet(pet.getId());
    assertEquals("Deleted Pet", returnMessage.getMessage());
  }
}