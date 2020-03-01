package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.repositories.PetRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PetDaoTest {

  @Autowired
  PetDao petDao;

  @Autowired
  PetRepository petRepository;

  @Mock
  Pet expected_pet;

  Pet pet;

  @Mock
  Message expected_message;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    petRepository.insert(pet);
  }

  @AfterEach
  public void cleanup() {
    petRepository.deleteAll();
  }

  @Test
  public void get_all_pets() {
    List<Pet> actual_pets = petDao.getAllPets();
    assertEquals(Collections.singletonList(pet), actual_pets);
  }

  @Test
  public void get_pet_by_id() {
    Pet actual_pet = petDao.getPetById(pet.getId());
    assertEquals(pet, actual_pet);
  }

  @Test
  public void create_pet() {
    pet.setId(pet.getId() + "999");
    pet.setName("Changed Pet");
    Pet actual_pet = petDao.createPet(pet);
    assertEquals(pet, actual_pet);
  }

  @Test
  public void put_pet() {
    pet.setName("Changed Pet");
    Pet returnedPet = petDao.putPet(pet);
    assertEquals(pet, returnedPet);

    Optional<Pet> updatedPet = petRepository.findById(pet.getId());
    assertEquals(pet, updatedPet.get());
  }

  @Test
  public void delete_pet() {
    Boolean deleteSuccess = petDao.deletePet(pet.getId());

    if (deleteSuccess) {
      assertEquals(false, petRepository.existsById(pet.getId()));
    } else {
      assertEquals(true, petRepository.existsById(pet.getId()));
    }

  }
}