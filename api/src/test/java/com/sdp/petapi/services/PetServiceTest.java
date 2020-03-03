package com.sdp.petapi.services;

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
public class PetServiceTest {

  @Autowired
  PetService petService;

  @Autowired
  PetRepository petRepository;

  Pet pet;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/main/resources/mocks/petObject.json"), Pet.class);
    petRepository.insert(pet);
  }

  @AfterEach
  public void cleanup() {
    petRepository.deleteAll();
  }

  @Test
  public void get_user_all_active_pets() {
    List<Pet> actual_pets = petService.getUserAllPets();
    actual_pets.forEach(pet -> assertNotNull(pet));
    assertEquals(Collections.singletonList(pet), actual_pets);
  }

  @Test
  public void get_employee_all_active_pets() {
    List<Pet> actual_pets = petService.getEmployeeAllPets();
    actual_pets.forEach(pet -> assertNotNull(pet));
    assertEquals(Collections.singletonList(pet), actual_pets);
  }

  @Test
  public void get_user_no_inactive_pets() {
    List<Pet> start_pets = petService.getUserAllPets();
    start_pets.forEach(pet -> assertNotNull(pet));
    assertEquals(Collections.singletonList(pet), start_pets);

    start_pets.forEach(pet -> pet.setActive(false));
    start_pets.forEach(pet -> assertFalse(pet.isActive()));

    start_pets.forEach(pet -> petService.updatePet(pet));

    List<Pet> actual_pets = petService.getUserAllPets();
    assertEquals(new ArrayList<Pet>(), actual_pets);
  }

  @Test
  public void get_employee_all_inactive_pets() {
    List<Pet> start_pets = petService.getEmployeeAllPets();
    start_pets.forEach(pet -> assertNotNull(pet));
    assertEquals(Collections.singletonList(pet), start_pets);

    start_pets.forEach(pet -> pet.setActive(false));
    start_pets.forEach(pet -> assertFalse(pet.isActive()));

    start_pets.forEach(pet -> petService.updatePet(pet));

    List<Pet> actual_pets = petService.getEmployeeAllPets();
    assertEquals(start_pets, actual_pets);
    assertFalse(new ArrayList<Pet>().equals(actual_pets));
    actual_pets.forEach(pet -> assertFalse(pet.isActive()));
  }

  @Test
  public void get_user_one_active_pet() {
    String id = pet.getId();
    Pet actual_pet = petService.getUserOnePet(id);
    assertAll(
      () -> assertNotNull(actual_pet),
      () -> assertEquals(actual_pet.getId(), id),
      () -> assertEquals(pet, actual_pet)
    );
  }

  @Test
  public void get_employee_one_active_pet() {
    String id = pet.getId();
    Pet actual_pet = petService.getEmployeeOnePet(id);
    assertAll(
      () -> assertNotNull(actual_pet),
      () -> assertEquals(actual_pet.getId(), id),
      () -> assertEquals(pet, actual_pet)
    );
  }

  @Test
  public void get_user_null_pet_for_inactive_pet() {
    String id = pet.getId();
    
    pet.setActive(false);
    assertFalse(pet.isActive());

    Pet returnedPet = petService.updatePet(pet);
    assertAll(
      () -> assertNotNull(returnedPet),
      () -> assertEquals(returnedPet.getId(), id),
      () -> assertFalse(returnedPet.isActive()),
      () -> assertEquals(pet, returnedPet)
    );

    Pet null_pet = petService.getUserOnePet(id);
    assertNull(null_pet);
  }

  @Test
  public void get_employee_one_inactive_pet() {
    String id = pet.getId();
    
    pet.setActive(false);
    assertFalse(pet.isActive());

    Pet returnedPet = petService.updatePet(pet);
    assertAll(
      () -> assertNotNull(returnedPet),
      () -> assertEquals(returnedPet.getId(), id),
      () -> assertFalse(returnedPet.isActive()),
      () -> assertEquals(pet, returnedPet)
    );

    Pet actual_pet = petService.getEmployeeOnePet(id);
    assertAll(
      () -> assertNotNull(actual_pet),
      () -> assertEquals(actual_pet.getId(), id),
      () -> assertFalse(actual_pet.isActive()),
      () -> assertEquals(pet, actual_pet)
    );
  }

  @Test
  public void get_user_null_pet_for_nonexistent_pet() {
    String id = pet.getId() + "999";
    Pet null_pet = petService.getUserOnePet(id);
    assertNull(null_pet);
  }

  @Test
  public void get_employee_null_pet_for_nonexistent_pet() {
    String id = pet.getId() + "999";
    Pet null_pet = petService.getEmployeeOnePet(id);
    assertNull(null_pet);
  }

  @Test
  public void get_user_null_pet_with_null_id() {
    Pet null_pet = petService.getUserOnePet(null);
    assertNull(null_pet);
  }

  @Test
  public void get_employee_null_pet_with_null_id() {
    Pet null_pet = petService.getEmployeeOnePet(null);
    assertNull(null_pet);
  }

  @Test
  public void add_real_pet() {
    String id = pet.getId();
    pet.setId(pet.getId() + "999");
    assertEquals(pet.getId(), id + "999");

    Pet actual_pet = petService.addPet(pet);
    assertAll(
      () -> assertNotNull(actual_pet),
      () -> assertEquals(actual_pet.getId(), id + "999"),
      () -> assertEquals(pet, actual_pet)
    );
  }

  @Test
  public void add_null_pet() {
    Pet null_pet = petService.addPet(null);
    assertNull(null_pet);
  }

  @Test
  public void update_real_pet() {
    pet.setName("Changed Pet");
    assertEquals(pet.getName(), "Changed Pet");

    Pet returnedPet = petService.updatePet(pet);
    assertAll(
      () -> assertNotNull(returnedPet),
      () -> assertEquals(pet.getId(), returnedPet.getId()),
      () -> assertEquals(pet.getName(), returnedPet.getName()),
      () -> assertEquals(pet, returnedPet)
    );

    Pet updatedPet = petRepository.findById(pet.getId()).get();
    assertAll(
      () -> assertNotNull(updatedPet),
      () -> assertEquals(pet.getId(), updatedPet.getId()),
      () -> assertEquals(pet.getName(), updatedPet.getName()),
      () -> assertEquals(pet, updatedPet)
    );
  }

  @Test
  public void update_null_pet() {
    Pet null_pet = petService.updatePet(null);
    assertNull(null_pet);
  }
}