package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.services.PetService;
import com.sdp.petapi.controllers.PetController;
import com.sdp.petapi.models.Pet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PetControllerTest {
  transient Pet pet;

  private static final String ID = "001";

  @Mock
  transient PetService petService;

  // makes a petService whose petDao is the mock above
  @InjectMocks
  transient PetController petController;

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
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petService.getAllPets()).thenReturn(Collections.singletonList(pet));
    List<Pet> list = petController.getAllPets();
    assertEquals(Collections.singletonList(pet), list);
  }
  
  @Test
  public void get_pet_by_id() {
    String id = ID;

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petService.getPetById(id)).thenReturn(pet);
    Pet actual_pet = petController.getPetById(id);
    assertEquals(pet, actual_pet);
  }

  @Test
  public void create_real_pet() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petService.createPet(pet)).thenReturn(pet);
    Pet returnPet = petController.createPet(pet);
    assertEquals(pet, returnPet);
  }

  @Test
  public void put_pet() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petService.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petController.putPet(ID, pet);
    assertEquals(pet, returnedPet);
  }

  @Test
  public void put_pet_null_id() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petService.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petController.putPet(null, pet);
    assertNull(returnedPet);
  }

  @Test
  public void put_pet_null_pet() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petService.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petController.putPet(ID, null);
    assertNull(returnedPet);
  }

  @Test
  public void put_pet_wrong_id() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petService.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petController.putPet("010", pet);
    assertNull(returnedPet);
  }

  @Test
  public void delete_pet() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petService.deletePet(ID)).thenReturn(pet);
    Pet returnedPet = petController.deletePet(ID);
    assertEquals(pet, returnedPet);
  }
  
}