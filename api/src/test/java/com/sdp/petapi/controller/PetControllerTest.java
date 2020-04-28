package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.services.PetService;
import com.sdp.petapi.controllers.PetController;
import com.sdp.petapi.models.Pet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

@SpringBootTest
public class PetControllerTest {
  transient Pet pet, pet2;

  private static final String ID001 = "001";
  private static final String ID002 = "002";

  @Mock
  transient PetService petService;

  @InjectMocks
  transient PetController petController;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    pet2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject2.json"), Pet.class);
    pet2.setActive(false);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService")
  public void get_all_pets_for_employee_returns_full_list() {
    when(petService.getAllPets()).thenReturn(Arrays.asList(new Pet[] {pet, pet2}));
    List<Pet> list = petController.getAllPets();
    assertEquals(Arrays.asList(new Pet[] {pet, pet2}), list);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService")
  public void get_all_pets_for_others_returns_active_pet_list() {
    when(petService.getAllPets()).thenReturn(Arrays.asList(new Pet[] {pet, pet2}));
    List<Pet> list = petController.getAllPets();
    assertEquals(Collections.singletonList(pet), list);
  }
  
  @Test
  public void get_actual_pet_by_id_returns_pet() {
    String id = ID001;
    when(petService.getPetById(id)).thenReturn(pet);
    Pet actual_pet = petController.getPetById(id);
    assertEquals(pet, actual_pet);
  }

  @Test
  public void get_null_pet_by_user_returns_null() {
    String id = ID002;
    when(petService.getPetById(id)).thenReturn(null);
    Pet actual_pet = petController.getPetById(id);
    assertNull(actual_pet);
  }

  @Test
  public void create_real_pet_makes_pet() {
    when(petService.createPet(pet)).thenReturn(pet);
    Pet returnPet = petController.createPet(pet);
    assertEquals(pet, returnPet);
  }

  @Test
  public void put_pet_updates_pet() {
    when(petService.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petController.putPet(ID001, pet);
    assertEquals(pet, returnedPet);
  }

  @Test
  public void put_pet_null_id_by_employee_returns_null() {
    when(petService.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petController.putPet(null, pet);
    assertNull(returnedPet);
  }

  @Test
  public void put_pet_null_pet_by_employee_retuns_null() {
    when(petService.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petController.putPet(ID001, null);
    assertNull(returnedPet);
  }

  @Test
  public void put_pet_wrong_id_by_employee_returns_null() {
    when(petService.putPet(pet)).thenReturn(pet);
    Pet returnedPet = petController.putPet("010", pet);
    assertNull(returnedPet);
  }

  @Test
  public void delete_pet() {
    when(petService.deletePet(ID001)).thenReturn(pet);
    Pet returnedPet = petController.deletePet(ID001);
    assertEquals(pet, returnedPet);
  }
  
}