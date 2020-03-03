package com.sdp.petapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collection;

import com.sdp.petapi.dao.PetDao;
// import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


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
  String expected_message;

  @Mock
  Collection<Pet> expected_collection_of_pets;

  @Test
  public void CreatesPetShouldWork() throws Exception {
    when(petService.addPet(expected_pet)).thenReturn(expected_pet);

    Pet actual_pet = petService.addPet(expected_pet);

    assertEquals(expected_pet, actual_pet);
  }
  
  @Test
  public void ReadPetShouldWork() throws Exception {

    when(petService.getPets()).thenReturn(expected_collection_of_pets);

    Collection<Pet> actual_collection_of_pets = petService.getPets();

    assertEquals(expected_collection_of_pets, actual_collection_of_pets);
  }
  /*
  @Test
<<<<<<< Updated upstream
  public void UpdatePetShouldWork() throws Exception {
=======
  public void delete_pet() {
    when(petDao.deletePet(pet.getId())).thenReturn(true);
    Boolean deleteSuccess = petService.deletePet(pet.getId());
    assertEquals(true, deleteSuccess);
  }*/
>>>>>>> Stashed changes

    when(petService.updatePet(expected_pet)).thenReturn(expected_message);

    String actual_message = petService.updatePet(expected_pet);

    assertEquals(expected_message, actual_message);
  }
}