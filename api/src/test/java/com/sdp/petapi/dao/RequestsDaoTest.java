package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.RequestInformation;
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
  transient UserDao userDao;
 
  @Autowired
  transient PetRepository petRepository;

  @Autowired
  transient UserRepository userRepository;

  @Autowired
  transient RequestsRepository reqRepository;

  transient Pet adoptedPet, pet2;
  transient User employee, webUser, webUser2;
  transient Requests req, req2;
  transient RequestInformation reqInfo;

  private static final String BADID = "010";
  private static final String ID001 = "001";
  private static final String ID002 = "002";
  private static final String ID003 = "003";
  private static final String STRING_CANCELED = "CANCELED";
  private static final String STRING_APPROVED = "APPROVED";
  private static final String STRING_PENDING = "PENDING";

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    adoptedPet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    adoptedPet.setActive(false);
    adoptedPet.setAdopted(true);
    petRepository.insert(adoptedPet);
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

    reqInfo = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestInfoObject.json"), RequestInformation.class);
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
  public void get_request_by_bad_id_returns_null() {
    Requests actual_req = reqDao.getRequestById(BADID);
    assertNull(actual_req);
  }

  @Test
  public void get_request_by_null_id_returns_null() {
    Requests actual_req = reqDao.getRequestById(null);
    assertNull(actual_req);
  }
  
  @Test
  public void get_good_request_by_id_returns_request() {
    Requests actual_req = reqDao.getRequestById(ID001);
    assertEquals(req, actual_req);
  }

  @Test
  public void create_request_is_good() {
    Pet orig_pet = petDao.getPetById(ID002);
    assertTrue(orig_pet.isActive());
    assertFalse(orig_pet.isAdopted());

    User real_user = userDao.getUserById(ID002);
    assertFalse(real_user.isEmployee());

    Date before_date = new Date();
    List<Requests> orig_list = reqDao.getAllRequests();
    Requests actual_req = reqDao.createRequest(ID002, ID002);
    List<Requests> updated_list = reqDao.getAllRequests();
    Date after_date = new Date();

    Pet updated_pet = petDao.getPetById(ID002);
    assertFalse(updated_pet.isActive());
    assertTrue(updated_pet.isAdopted());
    
    assertNotNull(actual_req);
    assertEquals(actual_req.getUserid(), ID002);
    assertEquals(actual_req.getPetid(), ID002);
    assertTrue(before_date.compareTo(actual_req.getRequestDate()) <= 0);
    assertTrue(after_date.compareTo(actual_req.getRequestDate()) >= 0);
    assertEquals(actual_req.getStatus(), STRING_PENDING);
    assertFalse(orig_list.contains(actual_req));
    assertTrue(updated_list.contains(actual_req));
    assertEquals(orig_list.size() + 1, updated_list.size());
  }

  @Test
  public void create_request_user_invalid() {
    Pet orig_pet = petDao.getPetById(ID002);
    assertTrue(orig_pet.isActive());
    assertFalse(orig_pet.isAdopted());

    User invalid_user = userDao.getUserById(BADID);
    assertNull(invalid_user);

    List<Requests> orig_list = reqDao.getAllRequests();
    Requests actual_req = reqDao.createRequest(BADID, ID002);
    List<Requests> updated_list = reqDao.getAllRequests();

    Pet updated_pet = petDao.getPetById(ID002);
    assertEquals(orig_pet, updated_pet);

    assertNull(actual_req);
    assertEquals(orig_list, updated_list);
  }

  @Test
  public void create_request_user_is_employee() {
    Pet orig_pet = petDao.getPetById(ID002);
    assertTrue(orig_pet.isActive());
    assertFalse(orig_pet.isAdopted());

    User real_user = userDao.getUserById(ID001);
    assertTrue(real_user.isEmployee());

    List<Requests> orig_list = reqDao.getAllRequests();
    Requests actual_req = reqDao.createRequest(ID001, ID002);
    List<Requests> updated_list = reqDao.getAllRequests();

    Pet updated_pet = petDao.getPetById(ID002);
    assertEquals(orig_pet, updated_pet);

    assertNull(actual_req);
    assertEquals(orig_list, updated_list);
  }

  @Test
  public void create_request_pet_invalid() {
    Pet orig_pet = petDao.getPetById(BADID);
    assertNull(orig_pet);

    User real_user = userDao.getUserById(ID002);
    assertFalse(real_user.isEmployee());

    List<Requests> orig_list = reqDao.getAllRequests();
    Requests actual_req = reqDao.createRequest(ID002, BADID);
    List<Requests> updated_list = reqDao.getAllRequests();

    assertNull(actual_req);
    assertEquals(orig_list, updated_list);
  }
  
  @Test
  public void create_request_pet_is_not_active() {
    assertEquals(pet2.getId(), ID002);
    pet2.setActive(false);
    petRepository.save(pet2);
    
    Pet orig_pet = petDao.getPetById(ID002);
    assertFalse(orig_pet.isActive());

    User real_user = userDao.getUserById(ID002);
    assertFalse(real_user.isEmployee());

    List<Requests> orig_list = reqDao.getAllRequests();
    Requests actual_req = reqDao.createRequest(ID002, ID002);
    List<Requests> updated_list = reqDao.getAllRequests();

    assertNull(actual_req);
    assertEquals(orig_list, updated_list);
  }
  
  @Test
  public void create_request_is_duplicate() {
    User real_user = userDao.getUserById(ID002);
    assertFalse(real_user.isEmployee());

    reqDao.cancelRequest(reqDao.createRequest(ID003, ID002).getId());
    reqDao.cancelRequest(reqDao.createRequest(ID002, ID002).getId());
    reqDao.createRequest(ID002, ID002);

    Pet change_pet = petDao.getPetById(ID002);
    change_pet.setActive(true);
    petDao.putPet(change_pet);

    List<Requests> orig_list = reqDao.getAllRequests();
    Requests actual_req = reqDao.createRequest(ID002, ID002);
    List<Requests> updated_list = reqDao.getAllRequests();

    assertNull(actual_req);
    assertEquals(orig_list, updated_list);
  }
  
  @Test
  public void put_request_approve() {
    List<Requests> orig_list = reqDao.getAllRequests();
    Requests updated_req = reqDao.putRequests(ID001, STRING_APPROVED);
    List<Requests> updated_list = reqDao.getAllRequests();

    assertEquals(updated_req.getStatus(), STRING_APPROVED);
    assertNotEquals(req, updated_req);
    assertNotEquals(orig_list, updated_list);
    assertEquals(orig_list.size(), updated_list.size());
    assertTrue(orig_list.contains(req));
    assertFalse(updated_list.contains(req));
    assertFalse(orig_list.contains(updated_req));
    assertTrue(updated_list.contains(updated_req));
  }

  @Test
  public void put_request_canceled() {
    List<Requests> orig_list = reqDao.getAllRequests();
    Requests updated_req = reqDao.putRequests(ID001, STRING_CANCELED);
    List<Requests> updated_list = reqDao.getAllRequests();

    assertEquals(updated_req.getStatus(), STRING_CANCELED);
    assertNotEquals(req, updated_req);
    assertNotEquals(orig_list, updated_list);
    assertEquals(orig_list.size(), updated_list.size());
    assertTrue(orig_list.contains(req));
    assertFalse(updated_list.contains(req));
    assertFalse(orig_list.contains(updated_req));
    assertTrue(updated_list.contains(updated_req));
  }

  @Test
  public void put_request_returns_null() {
    List<Requests> orig_list = reqDao.getAllRequests();
    Requests updated_req = reqDao.putRequests(ID001, "BAD_STATUS");
    List<Requests> updated_list = reqDao.getAllRequests();
    
    assertNull(updated_req);
    assertEquals(orig_list, updated_list);
  }

  @Test
  public void approve_request_other_requests_are_empty() {
    reqDao.deleteRequest(ID002);

    List<Requests> orig_list = reqDao.getAllRequests();
    Requests approved_req = reqDao.approveRequest(ID001);
    List<Requests> updated_list = reqDao.getAllRequests();

    assertEquals(approved_req.getStatus(), STRING_APPROVED);
    assertEquals(orig_list.size(), updated_list.size());
    assertEquals(orig_list.size(), 1);
  }

  @Test
  public void approve_request_explore_all_paths_cancelReqs() {
    Requests reqDiffUserDiffPet = reqDao.createRequest(ID003, ID002);
    Requests reqDiffUserSamePetCancel = reqDao.cancelRequest(ID002);
    adoptedPet.setActive(true);
    petDao.putPet(adoptedPet);
    Requests reqDiffUserSamePetPending = reqDao.createRequest(ID003, ID001);
    
    List<Requests> orig_list = reqDao.getAllRequests();
    Requests approved_req = reqDao.approveRequest(ID001);
    List<Requests> updated_list = reqDao.getAllRequests();

    assertEquals(approved_req.getStatus(), STRING_APPROVED);
    assertEquals(orig_list.size(), updated_list.size());
    assertFalse(orig_list.contains(approved_req));
    assertTrue(updated_list.contains(approved_req));
    assertEquals(reqDao.getRequestById(reqDiffUserDiffPet.getId()).getStatus(), STRING_PENDING);
    assertEquals(reqDao.getRequestById(reqDiffUserSamePetCancel.getId()).getStatus(), STRING_CANCELED);
    assertEquals(reqDao.getRequestById(reqDiffUserSamePetPending.getId()).getStatus(), STRING_CANCELED);
  }

  @Test
  public void cancel_request_other_requests_are_empty() {
    reqDao.deleteRequest(ID002);

    List<Requests> orig_list = reqDao.getAllRequests();
    Requests approved_req = reqDao.cancelRequest(ID001);
    List<Requests> updated_list = reqDao.getAllRequests();

    Pet updated_pet = petDao.getPetById(ID001);
    assertTrue(updated_pet.isActive());
    assertFalse(updated_pet.isAdopted());

    assertEquals(approved_req.getStatus(), STRING_CANCELED);
    assertEquals(orig_list.size(), updated_list.size());
    assertEquals(orig_list.size(), 1);
  }

  @Test
  public void cancel_request_explore_all_paths_reactivate_pet() {
    Requests reqDiffPet = reqDao.createRequest(ID002, ID002);
    Requests reqSamePetDiffUserCancel = reqDao.cancelRequest(ID002);
    adoptedPet.setActive(true);
    petDao.putPet(adoptedPet);
    Requests reqSamePetDiffUserPending = reqDao.createRequest(ID003, ID001);

    List<Requests> orig_list = reqDao.getAllRequests();
    Requests canceled_req = reqDao.cancelRequest(ID001);
    List<Requests> updated_list = reqDao.getAllRequests();

    assertEquals(canceled_req.getStatus(), STRING_CANCELED);
    assertEquals(orig_list.size(), updated_list.size());
    assertFalse(orig_list.contains(canceled_req));
    assertTrue(updated_list.contains(canceled_req));
    assertEquals(reqDao.getRequestById(reqDiffPet.getId()).getStatus(), STRING_PENDING);
    assertEquals(reqDao.getRequestById(reqSamePetDiffUserCancel.getId()).getStatus(), STRING_CANCELED);
    assertEquals(reqDao.getRequestById(reqSamePetDiffUserPending.getId()).getStatus(), STRING_PENDING);
  }

  @Test
  public void delete_request() {
    List<Requests> orig_req_list = reqDao.getAllRequests();

    Requests deleted_req = reqDao.deleteRequest(ID001);
    List<Requests> updated_req_list = reqDao.getAllRequests();

    assertEquals(req, deleted_req);
    assertTrue(orig_req_list.contains(req));
    assertFalse(updated_req_list.contains(req));
    assertEquals(orig_req_list.size(), updated_req_list.size() + 1);
  }

  @Test
  public void delete_request_with_bad_id_returns_null() {
    List<Requests> orig_req_list = reqDao.getAllRequests();

    Requests deleted_req = reqDao.deleteRequest(null);
    List<Requests> updated_req_list = reqDao.getAllRequests();

    assertNull(deleted_req);
    assertEquals(orig_req_list, updated_req_list);
  }

  @Test
  public void get_all_request_info() {
    reqDao.deleteRequest(ID002);
    List<RequestInformation> list_info = Collections.singletonList(reqInfo);
    
    List<RequestInformation> actual_info = reqDao.getAllRequestInfo();
    assertEquals(list_info, actual_info);
  }

  @Test
  public void get_all_request_info_no_pet_images() {
    reqDao.deleteRequest(ID002);
    adoptedPet.setImageNames(new String[0]);
    petDao.putPet(adoptedPet);
    reqInfo.setPetImage("N/A");
    List<RequestInformation> list_info = Collections.singletonList(reqInfo);
    
    List<RequestInformation> actual_info = reqDao.getAllRequestInfo();
    assertEquals(list_info, actual_info);
  }

  @Test
  public void get_request_info_by_invalid_id_returns_null() {
    RequestInformation actual_info = reqDao.getRequestInfoById(BADID);
    assertNull(actual_info);
  }
  
  @Test
  public void get_good_request_info_by_id_returns_request_info() {
    RequestInformation actual_info = reqDao.getRequestInfoById(ID001);
    assertEquals(reqInfo, actual_info);
  }
  
  @Test
  public void get_good_request_info_by_id_returns_request_info_no_pet_images() {
    reqInfo.setPetImage("N/A");
    adoptedPet.setImageNames(new String[0]);
    petDao.putPet(adoptedPet);
    RequestInformation actual_info = reqDao.getRequestInfoById(ID001);
    assertEquals(reqInfo, actual_info);
  }

}