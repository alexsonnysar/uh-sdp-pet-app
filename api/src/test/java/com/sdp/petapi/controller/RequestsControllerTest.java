package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.services.RequestsService;
import com.sdp.petapi.controllers.RequestsController;
import com.sdp.petapi.models.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestsControllerTest {
  Pet pet, pet2;
  User employee, webUser, webUser2;
  Requests req, req2;

  @Mock
  RequestsService reqService;

  // makes a reqController whose reqService is the mock above
  @InjectMocks
  RequestsController reqController;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    pet.setActive(false);
    pet.setAdopted(true);
    
    pet2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject2.json"), Pet.class);
    
    employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
    
    webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
    
    webUser2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject2.json"), User.class);
    
    req = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject.json"), Requests.class);
    
    req2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject2.json"), Requests.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_all_requests() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.getAllRequests()).thenReturn(Arrays.asList(new Requests[] {req, req2}));
    List<Requests> list = reqController.getAllRequests();
    assertEquals(Arrays.asList(new Requests[] {req, req2}), list);
  }
  
  @Test
  public void get_request_by_id() {
    String id = req.getId();

    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.getRequestById(id)).thenReturn(req);
    Requests actual_request = reqController.getRequestById(id);
    assertEquals(req, actual_request);
  }

  @Test
  public void create_request() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    Requests new_req = new Requests(webUser.getId(), pet2.getId());
    
    when(reqService.createRequest(webUser, pet2.getId())).thenReturn(new_req);
    
    Requests returnRequest = reqController.createRequest(pet2.getId(), webUser);
    assertEquals(new_req, returnRequest);
  }

  @Test
  public void put_request() {
    req.setStatus("APPROVED");
    assertEquals(req.getStatus(), "APPROVED");

    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.putRequest(employee, req)).thenReturn(req);
    Requests returnedRequest = reqController.putRequest(req.getId(), new RequestUser(req, employee));
    assertEquals(req, returnedRequest);

    when(reqService.getRequestById(req.getId())).thenReturn(req);
    Requests updatedRequest = reqController.getRequestById(req.getId());
    assertEquals(req, updatedRequest);
  }

  @Test
  public void put_request_with_null_id_return_null() {
    req.setStatus("APPROVED");
    assertEquals(req.getStatus(), "APPROVED");

    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.putRequest(employee, req)).thenReturn(req);
    Requests returnedRequest = reqController.putRequest(null, new RequestUser(req, employee));
    assertNull(returnedRequest);

    when(reqService.getRequestById(req.getId())).thenReturn(req);
    Requests updatedRequest = reqController.getRequestById(req.getId());
    assertEquals(req, updatedRequest);
  }

  @Test
  public void put_request_with_incorrect_id_returns_null() {
    req.setStatus("APPROVED");
    assertEquals(req.getStatus(), "APPROVED");

    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.putRequest(employee, req)).thenReturn(req);
    Requests returnedRequest = reqController.putRequest(req.getId() + "1", new RequestUser(req, employee));
    assertNull(returnedRequest);

    when(reqService.getRequestById(req.getId())).thenReturn(req);
    Requests updatedRequest = reqController.getRequestById(req.getId());
    assertEquals(req, updatedRequest);
  }

  @Test
  public void delete_request() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.getAllRequests()).thenReturn(Arrays.asList(new Requests[] {req, req2}));
    List<Requests> list = reqController.getAllRequests();
    assertEquals(Arrays.asList(new Requests[] {req, req2}), list);
    
    when(reqService.deleteRequest(req.getId())).thenReturn(req);
    Requests returnedRequest = reqController.deleteRequest(req.getId());
    assertEquals(req, returnedRequest);

    when(reqService.getRequestById(req.getId())).thenReturn(null);
    Requests updatedRequest = reqController.getRequestById(webUser.getId());
    assertNull(updatedRequest);
  }
  
}