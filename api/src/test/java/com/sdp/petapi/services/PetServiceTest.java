package com.sdp.petapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.dao.PetDao;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.repositories.PetRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PetServiceTest {
  Pet pet;

  @Mock
  PetDao petDao;

  // makes a petService whose petDao is the mock above
  @InjectMocks
  PetService petService;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_user_all_active_pets() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getUserAllPets()).thenReturn(Collections.singletonList(pet));
    List<Pet> list = petService.getUserAllPets();
    assertEquals(Collections.singletonList(pet), list);
  }

  @Test
  public void get_employee_all_active_pets() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getEmployeeAllPets()).thenReturn(Collections.singletonList(pet));
    List<Pet> list = petService.getEmployeeAllPets();
    assertEquals(Collections.singletonList(pet), list);
  }

  @Test
  public void get_user_no_inactive_pets() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getUserAllPets()).thenReturn(Collections.singletonList(pet));
    List<Pet> start_pets = petService.getUserAllPets();
    assertEquals(Collections.singletonList(pet), start_pets);

    start_pets.forEach(pet -> pet.setActive(false));
    start_pets.forEach(pet -> assertFalse(pet.isActive()));

    when(petDao.putPet(pet)).thenReturn(pet);
    start_pets.forEach(pet -> petService.putPet(pet));

    when(petDao.getUserAllPets()).thenReturn(new ArrayList<Pet>());
    List<Pet> actual_pets = petService.getUserAllPets();
    assertEquals(new ArrayList<Pet>(), actual_pets);
  }

  @Test
  public void get_employee_all_inactive_pets() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getEmployeeAllPets()).thenReturn(Collections.singletonList(pet));
    List<Pet> start_pets = petService.getEmployeeAllPets();
    assertEquals(Collections.singletonList(pet), start_pets);

    start_pets.forEach(pet -> pet.setActive(false));
    start_pets.forEach(pet -> assertFalse(pet.isActive()));

    when(petDao.putPet(pet)).thenReturn(pet);
    start_pets.forEach(pet -> petService.putPet(pet));

    when(petDao.getEmployeeAllPets()).thenReturn(Collections.singletonList(pet));
    List<Pet> actual_pets = petService.getEmployeeAllPets();
    assertEquals(Collections.singletonList(pet), actual_pets);
  }
  
  @Test
  public void get_user_one_active_pet() {
    String id = pet.getId();

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getUserPetById(id)).thenReturn(pet);
    Pet actual_pet = petService.getUserPetById(id);
    assertEquals(pet, actual_pet);
  }
  
  @Test
  public void get_employee_one_active_pet() {
    String id = pet.getId();

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getEmployeePetById(id)).thenReturn(pet);
    Pet actual_pet = petService.getEmployeePetById(id);
    assertEquals(pet, actual_pet);
  }
  
  @Test
  public void get_user_null_pet_for_inactive_pet() {
    String id = pet.getId();
    
    pet.setActive(false);
    assertFalse(pet.isActive());

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petService.putPet(pet);
    assertEquals(pet, returnedPet);

    when(petDao.getUserPetById(id)).thenReturn(null);
    Pet null_pet = petDao.getUserPetById(id);
    assertNull(null_pet);
  }

  @Test
  public void get_employee_one_inactive_pet() {
    String id = pet.getId();
    
    pet.setActive(false);
    assertFalse(pet.isActive());

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petService.putPet(pet);
    assertEquals(pet, returnedPet);

    when(petDao.getEmployeePetById(id)).thenReturn(pet);
    Pet actual_pet = petService.getEmployeePetById(id);
    assertEquals(actual_pet, pet);
  }

  @Test
  public void get_user_null_pet_for_nonexistent_pet() {
    String id = pet.getId() + "999";

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getUserPetById(id)).thenReturn(null);
    Pet null_pet = petService.getUserPetById(id);
    assertNull(null_pet);
  }

  @Test
  public void get_employee_null_pet_for_nonexistent_pet() {
    String id = pet.getId() + "999";

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getEmployeePetById(id)).thenReturn(null);
    Pet null_pet = petService.getEmployeePetById(id);
    assertNull(null_pet);
  }

  @Test
  public void get_user_null_pet_with_null_id() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getUserPetById(null)).thenReturn(null);
    Pet null_pet = petService.getUserPetById(null);
    assertNull(null_pet);
  }

  @Test
  public void get_employee_null_pet_with_null_id() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.getEmployeePetById(null)).thenReturn(null);
    Pet null_pet = petService.getEmployeePetById(null);
    assertNull(null_pet);
  }

  @Test
  public void create_real_pet() {
    String id = pet.getId();
    pet.setId(pet.getId() + "999");
    assertEquals(pet.getId(), id + "999");

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.createPet(pet)).thenReturn(pet);
    Pet returnPet = petService.createPet(pet);
    assertEquals(pet, returnPet);
  }

  @Test
  public void create_real_pet_no_id_given() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    List<String> images = new ArrayList<String>(pet.getImageNames());
    Pet new_pet = new Pet(pet.getName(), pet.getType(), pet.getSex(), pet.getAge(), pet.getSize(), pet.getWeight(), pet.getDescription(), images);
    
    when(petDao.createPet(new_pet)).thenReturn(pet);
    
    Pet returnPet = petService.createPet(new_pet);
    assertEquals(pet, returnPet);
  }

  @Test
  public void create_null_pet() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.createPet(null)).thenReturn(null);
    Pet null_pet = petService.createPet(null);
    assertNull(null_pet);
  }

  @Test
  public void put_real_pet() {
    pet.setName("Changed Pet");
    assertEquals(pet.getName(), "Changed Pet");

    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petService.putPet(pet);
    assertEquals(pet, returnedPet);

    when(petDao.getUserPetById(pet.getId())).thenReturn(pet);
    Pet updatedPet = petService.getUserPetById(pet.getId());
    assertEquals(pet, updatedPet);
  }

  @Test
  public void put_null_pet() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(petDao.putPet(null)).thenReturn(null);
    Pet null_pet = petService.putPet(null);
    assertNull(null_pet);
  }
}