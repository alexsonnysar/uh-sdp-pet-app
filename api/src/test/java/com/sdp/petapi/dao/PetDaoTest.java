package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.Requests;
import com.sdp.petapi.repositories.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PetDaoTest {

  @Autowired
  transient PetDao petDao;

  @Autowired
  transient PetRepository petRepository;

  @Autowired
  transient UserRepository userRepository;

  @Autowired
  transient RequestsRepository requestsRepository;

  transient Pet pet, pet2;

  transient Requests req0, req1, req2;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    petRepository.insert(pet);
    pet2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject2.json"), Pet.class);
    petRepository.insert(pet2);

    req0 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject.json"), Requests.class);
    req0.setId("000"); req0.setPetid("003");
    requestsRepository.insert(req0);
    req1 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject.json"), Requests.class);
    req1.setStatus("CANCELED");
    requestsRepository.insert(req1);
    req2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject2.json"), Requests.class);
    requestsRepository.insert(req2);
  }

  @AfterEach
  public void cleanup() {
    petRepository.deleteAll();
    userRepository.deleteAll();
    requestsRepository.deleteAll();
  }

  @Test
  public void get_all_pets() {
    List<Pet> actual_pets = petDao.getAllPets();
    assertEquals(Arrays.asList(new Pet[] {pet, pet2}), actual_pets);
  }

  @Test
  public void get_pet_by_good_id_returns_pet() {
    String id = "001";
    Pet actual_pet = petDao.getPetById(id);
    assertEquals(pet, actual_pet);
  }

  @Test
  public void get_pet_by_bad_id_returns_null() {
    String id = "010";
    Pet actual_pet = petDao.getPetById(id);
    assertNull(actual_pet);
  }

  @Test
  public void get_pet_by_null_id_returns_null() {
    Pet actual_pet = petDao.getPetById(null);
    assertNull(actual_pet);
  }

  @Test
  public void create_pet() {
    pet.setId(null);
    assertNull(pet.getId());

    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet inserted_pet = petDao.createPet(pet);
    List<Pet> updated_pet_list = petDao.getAllPets();

    assertNotNull(inserted_pet);
    assertNotNull(inserted_pet.getId());
    assertEquals(updated_pet_list.size(), orig_pet_list.size() + 1);
    assertFalse(orig_pet_list.contains(inserted_pet));
    assertTrue(updated_pet_list.contains(inserted_pet));
  }

  @Test
  public void create_pet_with_null_pet_returns_null() {
    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet inserted_pet = petDao.createPet(null);

    assertNull(inserted_pet);
    assertEquals(petDao.getAllPets(), orig_pet_list);
  }

  @Test
  public void create_pet_with_id_returns_null() {
    pet.setId("002");
    assertEquals(pet.getId(), "002");
    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet inserted_pet = petDao.createPet(pet);

    assertNull(inserted_pet);
    assertEquals(petDao.getAllPets(), orig_pet_list);
  }

  @Test
  public void put_pet() {
    pet.setName("Rad Park");
    assertEquals(pet.getName(), "Rad Park");

    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet updated_pet = petDao.putPet(pet);
    List<Pet> updated_pet_list = petDao.getAllPets();

    assertEquals(pet, updated_pet);
    assertEquals(updated_pet_list.size(), orig_pet_list.size());
    assertFalse(orig_pet_list.contains(pet));
    assertTrue(updated_pet_list.contains(pet));
  }

  @Test
  public void put_pet_with_null_pet_returns_null() {
    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet updated_pet = petDao.putPet(null);
    List<Pet> updated_pet_list = petDao.getAllPets();

    assertNull(updated_pet);
    assertEquals(updated_pet_list, orig_pet_list);

  }

  @Test
  public void put_pet_with_bad_id_returns_null() {
    pet.setName("Aymen");
    assertEquals(pet.getName(), "Aymen");
    pet.setId(null);
    assertNull(pet.getId());

    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet updated_pet = petDao.putPet(pet);
    List<Pet> updated_pet_list = petDao.getAllPets();

    assertNull(updated_pet);
    assertEquals(updated_pet_list, orig_pet_list);
    assertFalse(updated_pet_list.contains(pet));
  }

  @Test
  public void put_pet_deactivating_pet_auto_denies_pending_pet_requests() {
    Requests req0_db = requestsRepository.findById("000").get();
    Requests req1_db = requestsRepository.findById("001").get();
    Requests req2_db = requestsRepository.findById("002").get();
    assertEquals(req0.getStatus(), req0_db.getStatus());
    assertEquals(req1.getStatus(), req1_db.getStatus());
    assertEquals(req2.getStatus(), req2_db.getStatus());

    pet.setActive(false);

    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet updated_pet = petDao.putPet(pet);
    List<Pet> updated_pet_list = petDao.getAllPets();

    Requests updated_req0_db = requestsRepository.findById("000").get();
    Requests updated_req1_db = requestsRepository.findById("001").get();
    Requests updated_req2_db = requestsRepository.findById("002").get();

    assertEquals(pet, updated_pet);
    assertEquals(updated_pet_list.size(), orig_pet_list.size());
    assertFalse(orig_pet_list.contains(pet));
    assertTrue(updated_pet_list.contains(pet));
    assertEquals(req0.getStatus(), updated_req0_db.getStatus());
    assertEquals(req1.getStatus(), updated_req1_db.getStatus());
    assertNotEquals(req2.getStatus(), updated_req2_db.getStatus());
    assertEquals("CANCELED", updated_req2_db.getStatus());
  }

  @Test
  public void delete_pet() {
    String id = "001";

    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet deleted_pet = petDao.deletePet(id);
    List<Pet> updated_pet_list = petDao.getAllPets();

    assertEquals(pet, deleted_pet);
    assertTrue(orig_pet_list.contains(pet));
    assertFalse(updated_pet_list.contains(pet));
    assertEquals(orig_pet_list.size(), updated_pet_list.size() + 1);
  }

  @Test
  public void delete_pet_with_null_id_returns_null() {
    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet deleted_pet = petDao.deletePet(null);
    List<Pet> updated_pet_list = petDao.getAllPets();

    assertNull(deleted_pet);
    assertEquals(orig_pet_list, updated_pet_list);
  }

  @Test
  public void delete_nonexistent_pet_returns_null() {
    String id = "010";

    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet deleted_pet = petDao.deletePet(id);
    List<Pet> updated_pet_list = petDao.getAllPets();

    assertNull(deleted_pet);
    assertEquals(orig_pet_list, updated_pet_list);
  }

  @Test
  public void put_pet_by_request() {
    pet.setName("Josie");
    assertEquals(pet.getName(), "Josie");

    List<Pet> orig_pet_list = petDao.getAllPets();

    Pet updated_pet = petDao.putPetByRequest(pet);
    List<Pet> updated_pet_list = petDao.getAllPets();

    assertEquals(pet, updated_pet);
    assertEquals(petDao.getAllPets().size(), orig_pet_list.size());
    assertFalse(orig_pet_list.contains(pet));
    assertTrue(updated_pet_list.contains(pet));
  }

}