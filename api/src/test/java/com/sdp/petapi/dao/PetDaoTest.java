package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

// import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;

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
  List<Pet> expected_collection_of_pets;
  @Mock
  Pet expected_pet;
  @Mock
  String expected_message;

  @Test
  public void getPetsShouldWork() throws Exception {
    when(petDao.getPets()).thenReturn(expected_collection_of_pets);

    Collection<Pet> actual_collection_of_pets = petDao.getPets();

    assertEquals(expected_collection_of_pets, actual_collection_of_pets);

  }

  @Test
  public void CreatePetShouldWork() throws Exception {
    when(petDao.addPet(expected_pet)).thenReturn(expected_pet);

    Pet actual_pet = petDao.addPet(expected_pet);

    assertEquals(expected_pet, actual_pet);

  }

  @Test
  public void PutPetShouldWork() throws Exception {
    when(petDao.updatePet(expected_pet)).thenReturn(expected_message);

    String actual_message = petDao.updatePet(expected_pet);

    assertEquals(expected_message, actual_message);

  }

}