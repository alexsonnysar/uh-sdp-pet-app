package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.Requests;
import com.sdp.petapi.models.User;
import com.sdp.petapi.repositories.PetRepository;
import com.sdp.petapi.repositories.UserRepository;
import com.sdp.petapi.repositories.RequestsRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestsDaoTest {

  @Autowired
  RequestsDao reqDao;

  @Autowired
  PetDao petDao;

  @Autowired
  PetRepository petRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RequestsRepository reqRepository;

  Pet pet, pet2;
  User employee, webUser, webUser2;
  Requests req, req2;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    pet.setActive(false);
    pet.setAdopted(true);
    petRepository.insert(pet);
    pet2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject2.json"), Pet.class);
    petRepository.insert(pet2);
    
    employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
    userRepository.insert(employee);
    webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
    userRepository.insert(webUser);
    webUser2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject2.json"), User.class);
    userRepository.insert(webUser2);
    
    req = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject.json"), Requests.class);
    reqRepository.insert(req);
    req2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject2.json"), Requests.class);
    reqRepository.insert(req2);
  }

  @AfterEach
  public void cleanup() {
    petRepository.deleteAll();
    userRepository.deleteAll();
    reqRepository.deleteAll();
  }

  @Test
  public void get_all_requests() {
    List<Requests> actual_reqs = reqDao.getAllRequests();
    assertEquals(Arrays.asList(new Requests[] {req, req2}), actual_reqs);
  }

  @Test
  public void get_good_request_by_id_returns_request() {
    String id = req.getId();
    Requests actual_req = reqDao.getRequestById(id);
    assertEquals(req, actual_req);
  }

  @Test
  public void get_request_by_bad_id_returns_null() {
    String id = req.getId()+"1";
    Requests actual_req = reqDao.getRequestById(id);
    assertNull(actual_req);
  }

  @Test
  public void get_request_by_null_id_returns_null() {
    Requests actual_req = reqDao.getRequestById(null);
    assertNull(actual_req);
  }

  @Test
  public void create_request() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] {req, req2}));

    Requests inserted_req = reqDao.createRequest(webUser2, pet2.getId());
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(
      () -> assertNotNull(inserted_req),
      () -> assertNotNull(inserted_req.getId()),
      () -> assertEquals(updated_req_list.size(), orig_req_list.size() + 1),
      () -> assertFalse(orig_req_list.contains(inserted_req)),
      () -> assertTrue(updated_req_list.contains(inserted_req))
    );
  }

  @Test
  public void create_request_by_null_user_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] {req, req2}));

    Requests inserted_req = reqDao.createRequest(null, pet2.getId());
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(
      () -> assertNull(inserted_req),
      () -> assertEquals(updated_req_list, orig_req_list),
      () -> assertFalse(orig_req_list.contains(inserted_req))
    );
  }

  @Test
  public void create_request_by_employee_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] {req, req2}));

    Requests inserted_req = reqDao.createRequest(employee, pet2.getId());
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(
      () -> assertNull(inserted_req),
      () -> assertEquals(updated_req_list, orig_req_list),
      () -> assertFalse(orig_req_list.contains(inserted_req))
    );
  }

  @Test
  public void create_request_with_null_pet_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] {req, req2}));

    Requests inserted_req = reqDao.createRequest(webUser2, null);
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(
      () -> assertNull(inserted_req),
      () -> assertEquals(updated_req_list, orig_req_list),
      () -> assertFalse(orig_req_list.contains(inserted_req))
    );
  }

  @Test
  public void create_request_by_nonexistant_webUser_returns_null() {
    webUser2.setId("009");
    assertEquals(webUser2.getId(), "009");
    
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] {req, req2}));

    Requests inserted_req = reqDao.createRequest(webUser2, pet2.getId());
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(
      () -> assertNull(inserted_req),
      () -> assertEquals(updated_req_list, orig_req_list),
      () -> assertFalse(orig_req_list.contains(inserted_req))
    );
  }

  @Test
  public void create_request_by_bad_webUser_returns_null() {
    webUser2.setFirstName("Manny");
    assertEquals(webUser2.getFirstName(), "Manny");
    
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] {req, req2}));

    Requests inserted_req = reqDao.createRequest(webUser2, pet2.getId());
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(
      () -> assertNull(inserted_req),
      () -> assertEquals(updated_req_list, orig_req_list),
      () -> assertFalse(orig_req_list.contains(inserted_req))
    );
  }

  @Test
  public void create_request_with_nonexistent_pet_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] {req, req2}));

    Requests inserted_req = reqDao.createRequest(webUser2, pet.getId()+"1");
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(
      () -> assertNull(inserted_req),
      () -> assertEquals(updated_req_list, orig_req_list),
      () -> assertFalse(orig_req_list.contains(inserted_req))
    );
  }

  @Test
  public void create_request_with_inactive_pet_id_returns_null() {
    pet2.setActive(false);
    assertFalse(pet2.isActive());

    Pet inserted_pet = petDao.putPetByRequest(pet2);
    assertEquals(pet2, inserted_pet);

    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] {req, req2}));

    Requests inserted_req = reqDao.createRequest(webUser2, pet2.getId());
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(
      () -> assertNull(inserted_req),
      () -> assertEquals(updated_req_list, orig_req_list),
      () -> assertFalse(orig_req_list.contains(inserted_req))
    );
  }

  @Test
  public void create_request_for_existing_active_request_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] {req, req2}));

    Requests inserted_req = reqDao.createRequest(webUser, pet.getId());
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(
      () -> assertNull(inserted_req),
      () -> assertEquals(updated_req_list, orig_req_list),
      () -> assertFalse(orig_req_list.contains(inserted_req))
    );
  }

  @Test
  public void put_request_by_employee_approves_request() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setStatus("APPROVED");
    assertEquals(req.getStatus(), "APPROVED");

    Requests updated_req = reqDao.putRequests(employee, req);
    assertEquals(updated_req, req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertTrue(updated_req_list.contains(req));
    assertEquals(orig_req_list.size(), updated_req_list.size());
  }

  @Test
  public void put_request_by_employee_rejects_request() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setStatus("CANCELED");
    assertEquals(req.getStatus(), "CANCELED");

    Requests updated_req = reqDao.putRequests(employee, req);
    assertEquals(updated_req, req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertTrue(updated_req_list.contains(req));
    assertEquals(orig_req_list.size(), updated_req_list.size());
  }

  @Test
  public void put_request_by_webUser_cancels_request() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setStatus("CANCELED");
    assertEquals(req.getStatus(), "CANCELED");

    Requests updated_req = reqDao.putRequests(webUser, req);
    assertEquals(updated_req, req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertTrue(updated_req_list.contains(req));
    assertEquals(orig_req_list.size(), updated_req_list.size());
  }

  @Test
  public void put_request_by_null_user_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    Requests updated_req = reqDao.putRequests(null, req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertTrue(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_with_null_request_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();

    Requests updated_req = reqDao.putRequests(employee, null);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_by_user_for_another_users_request_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setStatus("CANCELED");
    assertEquals(req.getStatus(), "CANCELED");

    Requests updated_req = reqDao.putRequests(webUser2, req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_by_bad_user_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setStatus("CANCELED");
    assertEquals(req.getStatus(), "CANCELED");

    webUser.setFirstName("Joe");
    assertEquals(webUser.getFirstName(), "Joe");

    Requests updated_req = reqDao.putRequests(webUser, req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_with_nonexistent_request_in_db_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setId("abcd");
    assertEquals(req.getId(), "abcd");

    Requests updated_req = reqDao.putRequests(webUser, req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_with_incorrect_user_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setUserid("abcd");
    assertEquals(req.getUserid(), "abcd");

    webUser.setId("abcd");
    assertEquals(webUser.getId(), "abcd");

    Requests updated_req = reqDao.putRequests(webUser, req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_with_incorrect_pet_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setPetid("abcd");
    assertEquals(req.getPetid(), "abcd");

    Requests updated_req = reqDao.putRequests(webUser, req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_with_incorrect_date_returns_null() throws Exception {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    Date new_req_date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse("26-FEB-2018 18:16:17");
    req.setRequestDate(new_req_date);
    assertEquals(req.getRequestDate(), new_req_date);

    Requests updated_req = reqDao.putRequests(webUser, req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_webUser_approves_request_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setStatus("APPROVED");
    assertEquals(req.getStatus(), "APPROVED");

    Requests updated_req = reqDao.putRequests(webUser, req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_user_sends_pending_request_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));
    assertEquals(req.getStatus(), "PENDING");

    Requests updated_req = reqDao.putRequests(webUser, req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertTrue(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }


  @Test
  public void delete_request() {
    String id = req.getId();

    List<Requests> orig_req_list = reqDao.getAllRequests();
    
    Requests deleted_req = reqDao.deleteRequest(id);
    List<Requests> updated_req_list = reqDao.getAllRequests();

    assertAll(
      () -> assertEquals(req, deleted_req),
      () -> assertTrue(orig_req_list.contains(req)),
      () -> assertFalse(updated_req_list.contains(req)),
      () -> assertEquals(orig_req_list.size(), updated_req_list.size() + 1)
    );
  }

  @Test
  public void delete_request_with_bad_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    
    Requests deleted_req = reqDao.deleteRequest(null);
    List<Requests> updated_req_list = reqDao.getAllRequests();

    assertAll(
      () -> assertNull(deleted_req),
      () -> assertEquals(orig_req_list, updated_req_list)
    );
  }

  @Test
  public void approve_request_other_requests_for_pet_exist() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));
    
    req.setStatus("APPROVED");
    assertEquals(req.getStatus(), "APPROVED");
    req2.setStatus("CANCELED");
    assertEquals(req2.getStatus(), "CANCELED");
    
    Requests approved_req = reqDao.approveRequest(req);
    assertEquals(approved_req, req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertTrue(updated_req_list.contains(req));
    assertTrue(updated_req_list.contains(req2));

    Pet petdb = petDao.getPetById(pet.getId());
    assertEquals(pet, petdb);
  }

  @Test
  public void approve_request_no_other_requests_for_pet() {
    req.setStatus("CANCELED");
    assertEquals(req.getStatus(), "CANCELED");
    
    Requests canceled_req = reqDao.cancelRequest(req);
    assertEquals(canceled_req, req);
    
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));
    
    req2.setStatus("APPROVED");
    assertEquals(req2.getStatus(), "APPROVED");

    Requests approved_req = reqDao.approveRequest(req2);
    assertEquals(approved_req, req2);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertTrue(updated_req_list.contains(req));
    assertTrue(updated_req_list.contains(req2));

    Pet petdb = petDao.getPetById(pet.getId());
    assertEquals(pet, petdb);
  }

  @Test
  public void cancel_request_other_requests_for_pet_exist() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));
    
    req.setStatus("CANCELED");
    Requests canceled_req = reqDao.cancelRequest(req);
    assertEquals(canceled_req, req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertTrue(updated_req_list.contains(req));

    Pet petdb = petDao.getPetById(pet.getId());
    assertEquals(pet, petdb);
  }

  @Test
  public void cancel_request_no_other_requests_for_pet() {
    req.setStatus("CANCELED");
    reqDao.cancelRequest(req);
    
    req2.setStatus("CANCELED");
    Requests canceled_req_round2 = reqDao.cancelRequest(req2);
    assertEquals(canceled_req_round2, req2);

    pet.setActive(true);
    pet.setAdopted(false);
    Pet petdb_round2 = petDao.getPetById(pet.getId());
    assertEquals(pet, petdb_round2);

    List<Requests> updated_req_list_round2 = reqDao.getAllRequests();
    assertTrue(updated_req_list_round2.contains(req2));
  }
  
}