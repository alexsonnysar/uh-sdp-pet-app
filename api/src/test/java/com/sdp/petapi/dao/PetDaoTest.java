package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Pet;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PetDaoTest {

  @Autowired
  PetDao petDao;

  @Mock
  Pet expected_pet;

  @Mock
  Message expected_message;

  @Test
  public void get_pets_should_work() throws Exception {
    List<Pet> actual_pets = petDao.getPets();
    assertEquals(new ArrayList<Pet>(), actual_pets);
  }

  @Test
  public void create_pet_should_work() throws Exception {
    ObjectMapper om = new ObjectMapper();
    Pet pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);

    Pet actual_pet = petDao.createPet(pet);

    assertEquals(pet.hashCode(), actual_pet.hashCode());

  }

  @Test
  public void delete_pet_should_work() throws Exception {
    ObjectMapper om = new ObjectMapper();
    Pet pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);

    Message del_msg = new Message("Deleted Pet");
    Message actual_message = petDao.deletePet(pet.getId());

    assertEquals(del_msg, actual_message);

  }

  @Test
  public void put_pet_should_work() throws Exception {
    ObjectMapper om = new ObjectMapper();
    Pet pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    pet.setAge("He old now");
    Message update_msg = new Message("Updated Pet");
    Message actual_message = petDao.putPet(pet);
    assertEquals(update_msg, actual_message);
  }

}