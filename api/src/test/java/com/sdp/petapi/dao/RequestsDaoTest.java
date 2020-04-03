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
  transient RequestsDao reqDao;

  @Autowired
  transient PetDao petDao;

  @Autowired
  transient PetRepository petRepository;

  @Autowired
  transient UserRepository userRepository;

  @Autowired
  transient RequestsRepository reqRepository;

 transient Pet pet, pet2;
 transient User employee, webUser, webUser2;
 transient Requests req, req2;

  private static final String BADID = "010";

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
    assertEquals(Arrays.asList(new Requests[] { req, req2 }), actual_reqs);
  }

  @Test
  public void get_good_request_by_id_returns_request() {
    Requests actual_req = reqDao.getRequestById(pet.getId());
    assertEquals(req, actual_req);
  }

  @Test
  public void get_request_by_bad_id_returns_null() {
    Requests actual_req = reqDao.getRequestById( BADID);
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
    assertEquals(orig_req_list, Arrays.asList(new Requests[] { req, req2 }));

    Requests inserted_req = reqDao.createRequest(new Requests("002", "002"));
    List<Requests> updated_req_list = reqDao.getAllRequests();
    Pet updated_pet = petDao.getPetById("002");
    pet2.setActive(false);
    pet2.setAdopted(true);
    assertAll(() -> assertNotNull(inserted_req), () -> assertNotNull(inserted_req.getId()),
        () -> assertEquals(updated_req_list.size(), orig_req_list.size() + 1),
        () -> assertFalse(orig_req_list.contains(inserted_req)),
        () -> assertTrue(updated_req_list.contains(inserted_req)), () -> assertTrue(pet2.isAdopted()),
        () -> assertFalse(pet2.isActive()), () -> assertEquals(updated_pet, pet2));
  }

  @Test
  public void create_request_null_request_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] { req, req2 }));

    Requests inserted_req = reqDao.createRequest(null);
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(() -> assertNull(inserted_req), () -> assertEquals(updated_req_list, orig_req_list));
  }

  @Test
  public void create_request_with_request_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] { req, req2 }));

    req.setId(BADID);
    assertEquals(req.getId(), BADID);
    Requests inserted_req = reqDao.createRequest(req);
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(() -> assertNull(inserted_req), () -> assertEquals(updated_req_list, orig_req_list));
  }

  @Test
  public void create_request_with_null_user_id_returns_null() {
    Requests new_req = new Requests("002", "001");
    new_req.setStatus("CANCELED");
    Requests req3 = reqRepository.insert(new_req);
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] { req, req2, req3 }));

    Requests inserted_req = reqDao.createRequest(new Requests(null, "002"));
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(() -> assertNull(inserted_req), () -> assertEquals(updated_req_list, orig_req_list),
        () -> assertFalse(orig_req_list.contains(inserted_req)));
  }

  @Test
  public void create_request_with_employee_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] { req, req2 }));

    Requests inserted_req = reqDao.createRequest(new Requests("001", "002"));
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(() -> assertNull(inserted_req), () -> assertEquals(updated_req_list, orig_req_list));
  }

  @Test
  public void create_request_with_null_pet_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] { req, req2 }));

    Requests inserted_req = reqDao.createRequest(new Requests("002", null));
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(() -> assertNull(inserted_req), () -> assertEquals(updated_req_list, orig_req_list));
  }

  @Test
  public void create_request_by_nonexistant_user_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] { req, req2 }));

    Requests inserted_req = reqDao.createRequest(new Requests("009", "002"));
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(() -> assertNull(inserted_req), () -> assertEquals(updated_req_list, orig_req_list),
        () -> assertFalse(orig_req_list.contains(inserted_req)));
  }

  @Test
  public void create_request_with_nonexistent_pet_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] { req, req2 }));

    Requests inserted_req = reqDao.createRequest(new Requests("003", BADID));
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(() -> assertNull(inserted_req), () -> assertEquals(updated_req_list, orig_req_list),
        () -> assertFalse(orig_req_list.contains(inserted_req)));
  }

  @Test
  public void create_request_with_inactive_pet_id_returns_null() {
    pet2.setActive(false);
    assertFalse(pet2.isActive());

    Pet inserted_pet = petDao.putPetByRequest(pet2);
    assertEquals(pet2, inserted_pet);

    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] { req, req2 }));

    Requests inserted_req = reqDao.createRequest(new Requests("003", "002"));
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertAll(() -> assertNull(inserted_req), () -> assertEquals(updated_req_list, orig_req_list),
        () -> assertFalse(orig_req_list.contains(inserted_req)));
  }

  @Test
  public void create_request_for_existing_active_request_returns_null() {
    Requests new_req = new Requests("002", "002");
    new_req.setStatus("CANCELED");
    Requests new_req2 = new Requests("003", "002");
    new_req2.setStatus("CANCELED");
    Requests req3 = reqRepository.insert(new_req);
    Requests req4 = reqRepository.insert(new_req2);
    Requests req5 = reqDao.createRequest(new Requests("003", "002"));
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, Arrays.asList(new Requests[] { req, req2, req3, req4, req5 }));
    petDao.putPet(pet2);

    Requests inserted_req = reqDao.createRequest(new Requests("003", "002"));
    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertNull(inserted_req);
    assertEquals(updated_req_list, orig_req_list);
  }

  @Test
  public void put_request_approve_request() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setStatus("APPROVED");
    assertEquals(req.getStatus(), "APPROVED");

    Requests updated_req = reqDao.putRequests(req);
    assertEquals(updated_req, req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertTrue(updated_req_list.contains(req));
    assertEquals(orig_req_list.size(), updated_req_list.size());
  }

  @Test
  public void put_request_cancel_request() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setStatus("CANCELED");
    assertEquals(req.getStatus(), "CANCELED");

    Requests updated_req = reqDao.putRequests(req);
    assertEquals(updated_req, req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertTrue(updated_req_list.contains(req));
    assertEquals(orig_req_list.size(), updated_req_list.size());
  }

  @Test
  public void put_request_with_null_request_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();

    Requests updated_req = reqDao.putRequests(null);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_with_nonexistent_request_in_db_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setId(null);
    assertNull(req.getId());

    Requests updated_req = reqDao.putRequests(req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_with_nonexistent_user_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setUserid(null);
    assertNull(req.getUserid());

    Requests updated_req = reqDao.putRequests(req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_with_nonexistent_pet_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setPetid(null);
    assertNull(req.getPetid());

    Requests updated_req = reqDao.putRequests(req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_with_incorrect_user_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setUserid("003");
    assertEquals(req.getUserid(), "003");

    Requests updated_req = reqDao.putRequests(req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_with_incorrect_pet_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));

    req.setPetid("002");
    assertEquals(req.getPetid(), "002");

    Requests updated_req = reqDao.putRequests(req);
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

    Requests updated_req = reqDao.putRequests(req);
    assertNull(updated_req);

    List<Requests> updated_req_list = reqDao.getAllRequests();
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void put_request_pending_request_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();
    assertTrue(orig_req_list.contains(req));
    assertEquals(req.getStatus(), "PENDING");

    Requests updated_req = reqDao.putRequests(req);
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

    assertAll(() -> assertEquals(req, deleted_req), () -> assertTrue(orig_req_list.contains(req)),
        () -> assertFalse(updated_req_list.contains(req)),
        () -> assertEquals(orig_req_list.size(), updated_req_list.size() + 1));
  }

  @Test
  public void delete_request_with_bad_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();

    Requests deleted_req = reqDao.deleteRequest(null);
    List<Requests> updated_req_list = reqDao.getAllRequests();

    assertAll(() -> assertNull(deleted_req), () -> assertEquals(orig_req_list, updated_req_list));
  }

  @Test
  public void approve_request_other_requests_for_pet_exist() {
    Requests new_req = new Requests("003", "001");
    new_req.setStatus("CANCELED");
    reqRepository
        .saveAll(Arrays.asList(new Requests[] { new Requests("001", "002"), new Requests("002", "002"), new_req }));
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

    Pet petdb = petDao.getPetById("001");
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

    Pet petdb = petDao.getPetById("001");
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

    Pet petdb = petDao.getPetById("001");
    assertEquals(pet, petdb);
  }

  @Test
  public void cancel_request_no_other_requests_for_pet() {
    Requests req3 = reqRepository.insert(new Requests("003", "002"));
    assertEquals(reqDao.getAllRequests(), Arrays.asList(new Requests[] { req, req2, req3 }));
    req.setStatus("CANCELED");
    reqDao.cancelRequest(req);

    req2.setStatus("CANCELED");
    Requests canceled_req_round2 = reqDao.cancelRequest(req2);
    assertEquals(canceled_req_round2, req2);

    pet.setActive(true);
    pet.setAdopted(false);
    Pet petdb_round2 = petDao.getPetById("001");
    assertEquals(pet, petdb_round2);

    List<Requests> updated_req_list_round2 = reqDao.getAllRequests();
    assertTrue(updated_req_list_round2.contains(req2));
  }
}