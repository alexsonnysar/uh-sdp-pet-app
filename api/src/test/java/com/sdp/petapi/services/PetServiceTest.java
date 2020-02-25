package com.sdp.petapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import com.sdp.petapi.dao.PetDao;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import ch.qos.logback.classic.pattern.MessageConverter;

@SpringBootTest
public class PetServiceTest {

  @BeforeEach
  void initializeMockForEachTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Mock
  Pet expected_pet;

  @Mock
  PetService petService;

  @Mock
  PetDao petDao;

  @Mock
  Message expected_message;

  @Mock
  Collection<Pet> expected_collection_of_pets;

  @Test
  public void CreatesPetShouldWork() throws Exception {
    when(petService.createPet(expected_pet)).thenReturn(expected_pet);

    Pet actual_pet = petService.createPet(expected_pet);

    assertEquals(expected_pet, actual_pet);
  }

  @Test
  public void DeletePetShouldWork() throws Exception {
    when(petService.deletePet(expected_pet.getId())).thenReturn(expected_message);

    Message actual_message = petService.deletePet(expected_pet.getId());

    assertEquals(expected_message, actual_message);
  }

  @Test
  public void ReadPetShouldWork() throws Exception {

    when(petService.getPets()).thenReturn(expected_collection_of_pets);

    Collection<Pet> actual_collection_of_pets = petService.getPets();

    assertEquals(expected_collection_of_pets, actual_collection_of_pets);
  }

  @Test
  public void UpdatePetShouldWork() throws Exception {

    when(petService.putPet(expected_pet)).thenReturn(expected_message);

    Message actual_message = petService.putPet(expected_pet);

    assertEquals(expected_message, actual_message);
  }
}