package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.repositories.PetRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PetDaoTest {

  @Autowired
  PetDao petDao;

  @Autowired
  PetRepository petRepository;

  Pet pet;

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
  public void get_user_all_pets() {
    List<Pet> actual_pets = petDao.getUserAllPets();
    assertEquals(Collections.singletonList(pet), actual_pets);
  }

  @Test
  public void get_employee_all_pets() {
    List<Pet> actual_pets = petDao.getEmployeeAllPets();
    assertEquals(Collections.singletonList(pet), actual_pets);
  }

  @Test
  public void get_user_no_inactive_pets() {
    List<Pet> start_pets = petDao.getUserAllPets();
    assertEquals(Collections.singletonList(pet), start_pets);

    start_pets.forEach(pet -> pet.setActive(false));
    start_pets.forEach(pet -> assertFalse(pet.isActive()));

    start_pets.forEach(pet -> petDao.putPet(pet));

    List<Pet> actual_pets = petDao.getUserAllPets();
    assertEquals(new ArrayList<Pet>(), actual_pets);
  }

  @Test
  public void get_employee_all_inactive_pets() {
    List<Pet> start_pets = petDao.getEmployeeAllPets();
    assertEquals(Collections.singletonList(pet), start_pets);

    start_pets.forEach(pet -> pet.setActive(false));
    start_pets.forEach(pet -> assertFalse(pet.isActive()));

    start_pets.forEach(pet -> petDao.putPet(pet));

    List<Pet> actual_pets = petDao.getEmployeeAllPets();
    assertEquals(start_pets, actual_pets);
  }

  @Test
  public void get_user_one_active_pet() {
    String id = pet.getId();
    Pet actual_pet = petDao.getUserPetById(id);
    assertEquals(pet, actual_pet);
  }

  @Test
  public void get_employee_one_active_pet() {
    String id = pet.getId();
    Pet actual_pet = petDao.getEmployeePetById(id);
    assertEquals(pet, actual_pet);
  }

  @Test
  public void get_user_null_for_inactive_pet() {
    String id = pet.getId();
    
    pet.setActive(false);
    assertFalse(pet.isActive());

    Pet returnedPet = petDao.putPet(pet);
    assertEquals(pet, returnedPet);

    Pet null_pet = petDao.getUserPetById(id);
    assertNull(null_pet);
  }

  @Test
  public void get_employee_one_inactive_pet() {
    String id = pet.getId();
    
    pet.setActive(false);
    assertFalse(pet.isActive());

    Pet returnedPet = petDao.putPet(pet);
    assertEquals(pet, returnedPet);

    Pet actual_pet = petDao.getEmployeePetById(id);
    assertEquals(pet, actual_pet);
  }

  @Test
  public void get_user_null_pet_for_nonexistent_pet() {
    String id = pet.getId() + "999";
    Pet null_pet = petDao.getUserPetById(id);
    assertNull(null_pet);
  }

  @Test
  public void get_employee_null_pet_for_nonexistent_pet() {
    String id = pet.getId() + "999";
    Pet null_pet = petDao.getEmployeePetById(id);
    assertNull(null_pet);
  }

  @Test
  public void get_user_null_pet_with_null_id() {
    Pet null_pet = petDao.getUserPetById(null);
    assertNull(null_pet);
  }

  @Test
  public void get_employee_null_pet_with_null_id() {
    Pet null_pet = petDao.getEmployeePetById(null);
    assertNull(null_pet);
  }

  @Test
  public void create_real_pet() {
    String id = pet.getId();
    pet.setId(pet.getId() + "999");
    assertEquals(pet.getId(), id + "999");

    Pet actual_pet = petDao.createPet(pet);
    assertEquals(pet, actual_pet);
  }

  @Test
  public void create_real_pet_no_id_given() {
    List<String> images = new ArrayList<String>(pet.getImageNames());
    Pet new_pet = new Pet(pet.getName(), pet.getType(), pet.getSex(), pet.getAge(), pet.getSize(), pet.getWeight(), pet.getDescription(), images);

    Pet actual_pet = petDao.createPet(new_pet);
    assertAll(
      () -> assertNotNull(actual_pet),
      () -> assertNotNull(actual_pet.getId()),
      () -> assertEquals(new_pet.getName(), actual_pet.getName()),
      () -> assertEquals(new_pet.getType(), actual_pet.getType()),
      () -> assertEquals(new_pet.getSex(), actual_pet.getSex()),
      () -> assertEquals(new_pet.getAge(), actual_pet.getAge()),
      () -> assertEquals(new_pet.getSize(), actual_pet.getSize()),
      () -> assertEquals(new_pet.getWeight(), actual_pet.getWeight()),
      () -> assertEquals(new_pet.getDescription(), actual_pet.getDescription()),
      () -> assertEquals(new_pet.getImageNames(), actual_pet.getImageNames())
    );
  }

  @Test
  public void create_null_pet() {
    Pet null_pet = petDao.createPet(null);
    assertNull(null_pet);
  }
  
  @Test
  public void put_real_pet() {
    pet.setName("Changed Pet");
    assertEquals(pet.getName(), "Changed Pet");
    
    Pet returnedPet = petDao.putPet(pet);
    assertEquals(pet, returnedPet);

    Pet updatedPet = petDao.getUserPetById(pet.getId());
    assertEquals(pet, updatedPet);
  }

  @Test
  public void put_null_pet() {
    Pet null_pet = petDao.putPet(null);
    assertNull(null_pet);
  }
}