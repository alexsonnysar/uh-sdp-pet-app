package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collection;

import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.repositories.PetRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PetDaoTest {
  @BeforeEach
  void initializeMockForEachTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Mock
  PetDao petDao;
  @Mock
  Collection<Pet> expected_collection_of_pets;
  @Mock
  Pet expected_pet;
  @Mock
  Message expected_message;

  @Test
  public void getPetsShouldWork() throws Exception {
    when(petDao.getPets()).thenReturn(expected_collection_of_pets);

    Collection<Pet> actual_collection_of_pets = petDao.getPets();

    assertEquals(expected_collection_of_pets, actual_collection_of_pets);

  }

  @Test
  public void CreatePetShouldWork() throws Exception {
    when(petDao.createPet(expected_pet)).thenReturn(expected_pet);

    Pet actual_pet = petDao.createPet(expected_pet);

    assertEquals(expected_pet, actual_pet);

  }

  @Test
  public void deletePetShouldWork() throws Exception {
    when(petDao.deletePet(expected_pet.getId())).thenReturn(expected_message);

    Message actual_message = petDao.deletePet(expected_pet.getId());

    assertEquals(expected_message, actual_message);

  }

  @Test
  public void PutPetShouldWork() throws Exception {
    when(petDao.putPet(expected_pet)).thenReturn(expected_message);

    Message actual_message = petDao.putPet(expected_pet);

    assertEquals(expected_message, actual_message);

  }

}