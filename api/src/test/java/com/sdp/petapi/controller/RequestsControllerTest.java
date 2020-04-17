package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.services.RequestsService;
import com.sdp.petapi.controllers.RequestsController;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.Requests;
import com.sdp.petapi.models.User;
import com.sdp.petapi.repositories.PetRepository;
import com.sdp.petapi.repositories.UserRepository;
import com.sdp.petapi.security.UserDetailsImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

@SpringBootTest
public class RequestsControllerTest {

  private static final String ID001 = "001";
  private static final String ID002 = "002";
  private static final String ID003 = "003";

  transient Requests req;
  transient User employee, webUser, webUser2;
  transient Pet pet, pet2;

  @Mock
  transient RequestsService reqService;

  @Mock
  transient UserDetailsImpl userDeets;

  // makes a reqController whose reqService is the mock above
  @InjectMocks
  transient RequestsController reqController;

  transient UserRepository userRepo;
  transient PetRepository petRepo;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    req = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject.json"), Requests.class);
    
    employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
    webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
    webUser2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject2.json"), User.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_all_requests() {
    when(reqService.getAllRequests()).thenReturn(Collections.singletonList(req));
    List<Requests> list = reqController.getAllRequests();
    assertEquals(Collections.singletonList(req), list);
  }
  
  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void employee_get_request_by_id() {
    when(reqService.getRequestById(ID001)).thenReturn(req);
    when(userDeets.getId()).thenReturn(ID002);
    Requests actual_request = reqController.getRequestById(ID001);
    assertEquals(req, actual_request);
  }
  
  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_get_request_with_user_by_id_returns_request() {
    when(reqService.getRequestById(ID001)).thenReturn(req);
    when(userDeets.getId()).thenReturn(ID002);
    Requests actual_request = reqController.getRequestById(ID001);
    assertEquals(req, actual_request);
  }
  
  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_get_invalid_request_by_id_returns_null() {
    when(reqService.getRequestById(ID001)).thenReturn(null);
    when(userDeets.getId()).thenReturn(ID002);
    Requests actual_request = reqController.getRequestById(ID001);
    assertNull(actual_request);
  }
  
  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_get_request_with_different_user_by_id_returns_null() {
    when(reqService.getRequestById(ID001)).thenReturn(req);
    when(userDeets.getId()).thenReturn(ID003);
    Requests actual_request = reqController.getRequestById(ID001);
    assertNull(actual_request);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_creates_request() {
    when(reqService.createRequest(ID002, ID001)).thenReturn(req);
    Requests returnRequest = reqController.createRequest(ID001);
    assertEquals(req, returnRequest);
  }

  // @Test
  // public void put_request() {
  //   when(reqService.putRequest(req)).thenReturn(req);
  //   Requests returnedRequest = reqController.putRequest(ID001, req);
  //   assertEquals(req, returnedRequest);
  // }

  // @Test
  // public void put_request_with_null_id_return_null() {
  //   when(reqService.putRequest(req)).thenReturn(req);
  //   Requests returnedRequest = reqController.putRequest(null, req);
  //   assertNull(returnedRequest);
  // }

  // @Test
  // public void put_request_with_incorrect_id_returns_null() {
  //   when(reqService.putRequest(req)).thenReturn(req);
  //   Requests returnedRequest = reqController.putRequest("010", req);
  //   assertNull(returnedRequest);
  // }

  @Test
  public void delete_request() {
    when(reqService.deleteRequest(ID001)).thenReturn(req);
    Requests returnedRequest = reqController.deleteRequest(ID001);
    assertEquals(req, returnedRequest);
  }
  
}