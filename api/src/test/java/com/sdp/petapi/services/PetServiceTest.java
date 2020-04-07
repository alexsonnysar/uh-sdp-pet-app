package com.sdp.petapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.dao.PetDao;
import com.sdp.petapi.models.Pet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PetServiceTest {
  transient Pet pet;

  @Mock
  transient PetDao petDao;

  @InjectMocks
  transient PetService petService;

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
    when(petDao.getAllPets()).thenReturn(Collections.singletonList(pet));
    List<Pet> list = petService.getAllPets();
    assertEquals(Collections.singletonList(pet), list);
  }
  
  @Test
  public void get_pet_by_id() {
    String id = "001";
    when(petDao.getPetById(id)).thenReturn(pet);
    Pet actual_pet = petService.getPetById(id);
    assertEquals(pet, actual_pet);
  }

  @Test
  public void create_real_pet() {
    when(petDao.createPet(pet)).thenReturn(pet);
    Pet returnPet = petService.createPet(pet);
    assertEquals(pet, returnPet);
  }

  @Test
  public void put_pet() {
    when(petDao.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petService.putPet(pet);
    assertEquals(pet, returnedPet);
  }

  @Test
  public void delete_pet() {
    when(petDao.deletePet("001")).thenReturn(pet);
    Pet returnedPet = petService.deletePet("001");
    assertEquals(pet, returnedPet);
  }

  @Test
  public void put_pet_by_request() {
    when(petDao.putPetByRequest(pet)).thenReturn(pet);
    Pet returnedPet = petService.putPetByRequest(pet);
    assertEquals(pet, returnedPet);
  }
}