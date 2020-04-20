package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestsDaoTest {

  @Mock
  transient UserDao mockUserDao;

  @Mock
  transient PetDao mockPetDao;

  @Mock
  transient RequestsRepository mockRequestsRepository;

  @InjectMocks
  transient RequestsDao injectRequestsDao;

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

  transient Pet AdoptedPet, pet2;
  transient User employee, webUser, webUser2;
  transient Requests req, req2;
  transient RequestInformation reqInfo;
  transient List<RequestInformation> reqInfoList;

  private static final String BADID = "010";
  private static final String ID001 = "001";
  private static final String ID002 = "002";
  private static final String ID003 = "003";
  private static final String STRING_CANCELED = "CANCELED";
  private static final String STRING_APPROVED = "APPROVED";
  private static final String STRING_PENDING = "PENDING";
  private static final String DATEFORMAT = "dd-MMM-yyyy HH:mm:ss";
  private static final String FEBDATE = "26-FEB-2020 00:16:17";
  // transient Date new_req_date;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    AdoptedPet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    AdoptedPet.setActive(false);
    AdoptedPet.setAdopted(true);
    petRepository.insert(AdoptedPet);
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
    when(mockRequestsRepository.findAll()).thenReturn(Arrays.asList(new Requests[] { req, req2 }));
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
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    Requests actual_req = injectRequestsDao.getRequestById(ID001);
    assertEquals(req, actual_req);
  }

  @Test
  public void create_request_user_invalid() {
    when(mockUserDao.getUserById(ID001)).thenReturn(null);
    Requests actual_req = injectRequestsDao.createRequest(ID001, ID002);
    assertEquals(null, actual_req);
  }

  @Test
  public void create_request_user_is_employee() {
    employee.setEmployee(true);
    when(mockUserDao.getUserById(ID001)).thenReturn(employee);
    Requests actual_req = injectRequestsDao.createRequest(ID001, ID002);
    assertEquals(null, actual_req);
  }

  @Test
  public void create_request_pet_is_null() {
    webUser.setEmployee(false);
    when(mockUserDao.getUserById(ID001)).thenReturn(webUser);

    when(mockPetDao.getPetById(ID002)).thenReturn(null);
    Requests actual_req = injectRequestsDao.createRequest(ID001, ID002);
    assertEquals(null, actual_req);
  }
  
  @Test
  public void create_request_pet_is_not_active() {
    webUser.setEmployee(false);
    when(mockUserDao.getUserById(ID001)).thenReturn(webUser);

    when(mockPetDao.getPetById(ID002)).thenReturn(AdoptedPet);
    Requests actual_req = injectRequestsDao.createRequest(ID001, ID002);
    assertEquals(null, actual_req);
  }
  
  @Test
  public void create_request_is_duplicate() {
    webUser.setEmployee(false);
    when(mockUserDao.getUserById(ID001)).thenReturn(webUser);
    when(mockPetDao.getPetById(ID002)).thenReturn(pet2);

    req.setPetid("002");
    req.setUserid("001");
    req.setStatus("PENDING");
    when(mockRequestsRepository.findAll()).thenReturn(Arrays.asList(new Requests[] { req }));
    Requests actual_req = injectRequestsDao.createRequest(ID001, ID002);
    assertEquals(null, actual_req);
  }

  @Test
  public void create_request_is_good() throws Exception {
    webUser.setEmployee(false);

    when(mockUserDao.getUserById(ID001)).thenReturn(webUser);

    pet2.setActive(true);
    when(mockPetDao.getPetById(ID002)).thenReturn(pet2);

    Requests reqPetDup = new Requests("010", "010", "002", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "PENDING");
    Requests reqUserDup = new Requests("010", "001", "002", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "CANCELED");
    Requests reqDiffPet = new Requests("010", "010", "010", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "PENDING");
    when(mockRequestsRepository.findAll()).thenReturn(Arrays.asList(new Requests[] {reqPetDup, reqUserDup, reqDiffPet}));
    when(mockPetDao.putPetByRequest(pet2)).thenReturn(pet2);

    when(mockRequestsRepository.insert(any(Requests.class))).thenReturn(req);

    Requests actual_req = injectRequestsDao.createRequest(ID001, ID002);

    assertEquals(req, actual_req);
  }
  
  @Test
  public void put_request_approve() {
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    when(mockRequestsRepository.findAll()).thenReturn(Arrays.asList(new Requests[] {}));
    when(mockRequestsRepository.save(any(Requests.class))).thenReturn(req);

    Requests actualRequest = injectRequestsDao.putRequests(ID001, STRING_APPROVED);
    assertEquals(req, actualRequest);
  }

  @Test
  public void put_request_canceled() {
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    when(mockRequestsRepository.findAll()).thenReturn(Arrays.asList(new Requests[] {}));
    when(mockPetDao.getPetById(anyString())).thenReturn(AdoptedPet);
    when(mockRequestsRepository.save(any(Requests.class))).thenReturn(req);

    Requests actualRequest = injectRequestsDao.putRequests(ID001, STRING_CANCELED);
    assertEquals(req, actualRequest);
  }

  @Test
  public void put_request_returns_null() {
    Requests actualRequest = injectRequestsDao.putRequests(ID001, "BAD_STATUS");
    assertNull(actualRequest);
  }

  @Test
  public void approve_request_other_requests_are_empty() {

    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    when(mockRequestsRepository.findAll()).thenReturn(Arrays.asList(new Requests[] {}));
    when(mockRequestsRepository.save(any(Requests.class))).thenReturn(req);

    Requests actualRequest = injectRequestsDao.approveRequest(ID001);
    assertEquals(req, actualRequest);
  }

  @Test
  public void approve_request_other_requests_not_empty() throws Exception {
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    Requests reqSameUser = new Requests("010", "002", "002", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "CANCELED");
    Requests reqDiffUserSamePet = new Requests("011", "001", "001", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "CANCELED");
    Requests reqDiffUserDiffPet = new Requests("012", "001", "010", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "PENDING");
    when(mockRequestsRepository.findAll()).thenReturn(Arrays.asList(new Requests[] { req2, reqSameUser, reqDiffUserSamePet, reqDiffUserDiffPet }));
    when(mockRequestsRepository.save(any(Requests.class))).thenReturn(req);

    Requests actualRequest = injectRequestsDao.approveRequest(ID001);
    assertEquals(req, actualRequest);
  }

  @Test
  public void cancel_request_other_requests_are_empty() {
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    when(mockRequestsRepository.findAll()).thenReturn(Arrays.asList(new Requests[] {  }));    
    when(mockPetDao.getPetById(anyString())).thenReturn(AdoptedPet);
    when(mockRequestsRepository.save(any(Requests.class))).thenReturn(req);

    Requests actualRequest = injectRequestsDao.cancelRequest(ID001);
    assertEquals(req, actualRequest);
  }

  @Test
  public void cancel_request_other_requests_not_empty() throws Exception {
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    Requests reqSamePetSameUser = new Requests("010", "002", "001", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "CANCELED");
    Requests reqSamePetDiffUser = new Requests("011", "010", "001", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "CANCELED");
    Requests reqSamePetDiffUserPending = new Requests("012", "010", "001", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "PENDING");
    Requests reqDiffPet = new Requests("013", "010", "010", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "PENDING");
    when(mockRequestsRepository.findAll()).thenReturn(Arrays.asList(new Requests[] {reqDiffPet, reqSamePetSameUser, reqSamePetDiffUser, reqSamePetDiffUserPending}));
    when(mockPetDao.getPetById(anyString())).thenReturn(AdoptedPet);
    when(mockRequestsRepository.save(any(Requests.class))).thenReturn(req);

    Requests actualRequest = injectRequestsDao.cancelRequest(ID001);
    assertEquals(req, actualRequest);
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
  public void get_all_request_info() throws Exception {
    List<RequestInformation> list_info = Arrays.asList(new RequestInformation[] {
      new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING"),
      new RequestInformation(ID002, ID003, "Anthony Stark", "stark@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse("26-FEB-2020 01:16:17"), "PENDING")
    }).stream().collect(Collectors.toList());
    
    List<RequestInformation> actual_info = reqDao.getAllRequestInfo();
    System.out.println(list_info.getClass());
    System.out.println(actual_info.getClass());
    assertEquals(list_info.toString(), actual_info.toString());
  }

  @Test
  public void get_request_info_by_bad_id_returns_null() throws Exception {
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.empty());
    RequestInformation actual_info = injectRequestsDao.getRequestInfoById(BADID);
    assertNull(actual_info);
  }

  @Test
  public void get_request_info_by_null_id_returns_null() throws Exception {
    reqInfo = new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING");
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    RequestInformation actual_info = injectRequestsDao.getRequestInfoById(null);
    assertNull(actual_info);
  }
  
  @Test
  public void get_good_request_info_by_id_returns_request_info() throws Exception {
    reqInfo = new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "walking in the park", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING");
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    when(mockUserDao.getUserById(anyString())).thenReturn(webUser);
    when(mockPetDao.getPetById(anyString())).thenReturn(AdoptedPet);
    RequestInformation actual_info = injectRequestsDao.getRequestInfoById(ID001);
    assertEquals(reqInfo.toString(), actual_info.toString());
  }
  
  @Test
  public void get_good_request_info_by_id_returns_request_info_pet_images_null() throws Exception {
    reqInfo = new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING");
    AdoptedPet.setImageNames(null);
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    when(mockUserDao.getUserById(anyString())).thenReturn(webUser);
    when(mockPetDao.getPetById(anyString())).thenReturn(AdoptedPet);
    RequestInformation actual_info = injectRequestsDao.getRequestInfoById(ID001);
    assertEquals(reqInfo.toString(), actual_info.toString());
  }
  
  @Test
  public void get_good_request_info_by_id_returns_request_info_no_pet_images() throws Exception {
    reqInfo = new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING");
    AdoptedPet.setImageNames(new String[0]);
    when(mockRequestsRepository.findById(anyString())).thenReturn(Optional.of(req));
    when(mockUserDao.getUserById(anyString())).thenReturn(webUser);
    when(mockPetDao.getPetById(anyString())).thenReturn(AdoptedPet);
    RequestInformation actual_info = injectRequestsDao.getRequestInfoById(ID001);
    assertEquals(reqInfo.toString(), actual_info.toString());
  }

}