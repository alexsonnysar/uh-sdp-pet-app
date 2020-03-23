package com.sdp.petapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.dao.PetDao;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PetServiceTest {
  Pet pet;
  User employee;

  @Mock
  PetDao petDao;

  // makes a petService whose petDao is the mock above
  @InjectMocks
  PetService petService;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    
    employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_all_pets() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getAllPets()).thenReturn(Collections.singletonList(pet));
    List<Pet> list = petService.getAllPets();
    assertEquals(Collections.singletonList(pet), list);
  }
  
  @Test
  public void get_pet_by_id() {
    String id = pet.getId();

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getPetById(id)).thenReturn(pet);
    Pet actual_pet = petService.getPetById(id);
    assertEquals(pet, actual_pet);
  }

  @Test
  public void create_real_pet() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    Pet new_pet = new Pet(pet.getName(), pet.getType(), pet.getSex(), pet.getAge(), pet.getSize(), pet.getWeight(), pet.getDescription(), pet.getImageNames());
    
    when(petDao.createPet(employee, new_pet)).thenReturn(pet);
    
    Pet returnPet = petService.createPet(employee, new_pet);
    assertEquals(pet, returnPet);
  }

  @Test
  public void put_pet() {
    pet.setName("Changed Pet");
    assertEquals(pet.getName(), "Changed Pet");

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.putPet(employee, pet)).thenReturn(pet);
    Pet returnedPet = petService.putPet(employee, pet);
    assertEquals(pet, returnedPet);

    when(petDao.getPetById(pet.getId())).thenReturn(pet);
    Pet updatedPet = petService.getPetById(pet.getId());
    assertEquals(pet, updatedPet);
  }

  @Test
  public void delete_pet() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getAllPets()).thenReturn(Collections.singletonList(pet));
    List<Pet> list = petService.getAllPets();
    assertEquals(Collections.singletonList(pet), list);
    
    when(petDao.deletePet(pet.getId())).thenReturn(pet);
    Pet returnedPet = petService.deletePet(pet.getId());
    assertEquals(pet, returnedPet);

    when(petDao.getPetById(pet.getId())).thenReturn(null);
    Pet updatedPet = petService.getPetById(pet.getId());
    assertNull(updatedPet);
  }

  @Test
  public void put_pet_by_request() {
    pet.setName("Changed Pet");
    assertEquals(pet.getName(), "Changed Pet");

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.putPetByRequest(pet)).thenReturn(pet);
    Pet returnedPet = petService.putPetByRequest(pet);
    assertEquals(pet, returnedPet);

    when(petDao.getPetById(pet.getId())).thenReturn(pet);
    Pet updatedPet = petService.getPetById(pet.getId());
    assertEquals(pet, updatedPet);
  }
  
}