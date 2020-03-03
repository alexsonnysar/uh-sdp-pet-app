package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

<<<<<<< Updated upstream
import java.util.Collection;
import java.util.List;
=======
import java.io.File;
//import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
//import java.util.stream.*;
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
  public void CreatePetShouldWork() throws Exception {
    when(petDao.addPet(expected_pet)).thenReturn(expected_pet);
=======
  public void get_pet_by_id_returns_empty_pet() {
    // This id should not be in the database
    Pet actual_pet = petDao.getPetById(pet.getId()+"999");
    assertEquals(null, actual_pet);
  }
>>>>>>> Stashed changes

    Pet actual_pet = petDao.addPet(expected_pet);

    assertEquals(expected_pet, actual_pet);

  }

  /*
  @Test
  public void PutPetShouldWork() throws Exception {
    when(petDao.updatePet(expected_pet)).thenReturn(expected_message);

    String actual_message = petDao.updatePet(expected_pet);

    assertEquals(expected_message, actual_message);

<<<<<<< Updated upstream
  }

=======
  }*/
>>>>>>> Stashed changes
}