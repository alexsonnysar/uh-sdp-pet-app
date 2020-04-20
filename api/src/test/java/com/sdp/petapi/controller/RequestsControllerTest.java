package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.services.RequestsService;
import com.sdp.petapi.controllers.RequestsController;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.RequestInformation;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;

@SpringBootTest
public class RequestsControllerTest {

  private static final String ID001 = "001";
  private static final String ID002 = "002";
  private static final String DATEFORMAT = "dd-MMM-yyyy HH:mm:ss";
  private static final String FEBDATE = "26-FEB-2020 18:16:17";

  transient Requests req, dbReq;
  transient User employee, webUser, webUser2;
  transient Pet pet, pet2;
  transient RequestInformation reqInfo;
  transient List<RequestInformation> reqInfoList;

  @Mock
  transient RequestsService reqService;

  @Mock
  transient UserDetailsImpl userDeets;
  
  @Mock
  transient Authentication authentication;
  
  @Mock
  transient SecurityContext securityContext;

  @InjectMocks
  transient RequestsController reqController;

  transient UserRepository userRepo;
  transient PetRepository petRepo;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    req = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject.json"), Requests.class);
    dbReq = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject.json"), Requests.class);
    
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
    Requests actual_request = reqController.getRequestById(ID001);
    assertEquals(req, actual_request);
  }
  
  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_get_invalid_request_by_id_returns_null() {
    when(reqService.getRequestById(ID001)).thenReturn(null);
    Requests actual_request = reqController.getRequestById(ID001);
    assertNull(actual_request);
  }
  
  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_get_request_with_different_user_by_id_returns_null() {
    req.setUserid("BAD_ID");
    when(reqService.getRequestById(ID001)).thenReturn(req);
    Requests actual_request = reqController.getRequestById(ID001);
    assertNull(actual_request);
  }

  @Test
  public void get_all_request_info() throws Exception {
    reqInfoList = Collections.singletonList(new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING"));
    when(reqService.getAllRequestInfo()).thenReturn(reqInfoList);
    List<RequestInformation> list = reqController.getAllRequestInfo();
    assertEquals(reqInfoList, list);
  }
  
  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void employee_get_request_info_by_id() throws Exception {
    reqInfo = new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING");
    when(reqService.getRequestInfoById(ID001)).thenReturn(reqInfo);
    RequestInformation actual_info = reqController.getRequestInfoById(ID001);
    assertEquals(reqInfo, actual_info);
  }
  
  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_get_request_info_with_user_by_id_returns_request() throws Exception {
    reqInfo = new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING");
    when(reqService.getRequestInfoById(ID001)).thenReturn(reqInfo);
    RequestInformation actual_info = reqController.getRequestInfoById(ID001);
    assertEquals(reqInfo, actual_info);
  }
  
  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_get_invalid_request_info_by_id_returns_null() throws Exception {
    when(reqService.getRequestInfoById(ID001)).thenReturn(null);
    RequestInformation actual_info = reqController.getRequestInfoById(ID001);
    assertEquals(reqInfo, actual_info);
  }
  
  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_get_request_info_with_different_user_by_id_returns_null() throws Exception {
    reqInfo = new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING");
    req.setUserid("BAD_ID");
    when(reqService.getRequestInfoById(ID001)).thenReturn(reqInfo);
    RequestInformation actual_info = reqController.getRequestInfoById(ID001);
    assertEquals(reqInfo, actual_info);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_creates_request() {
    when(reqService.createRequest(ID002, ID001)).thenReturn(req);
    Requests returnRequest = reqController.createRequest(ID001);
    assertEquals(req, returnRequest);
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void employee_put_request_request_null() {
    when(reqService.getRequestById(ID001)).thenReturn(null);

    Requests returnRequest = reqController.putRequest(ID001, "APPROVED");
    assertEquals(null, returnRequest);
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void employee_put_request_request_bad_status() {
    dbReq.setStatus("BAD STATUS");
    when(reqService.getRequestById(ID001)).thenReturn(dbReq);

    Requests returnRequest = reqController.putRequest(ID001, "APPROVED");
    assertEquals(null, returnRequest);
  }
  
  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void employee_put_request_null_status() {
    dbReq.setStatus("PENDING");
    when(reqService.getRequestById(ID001)).thenReturn(dbReq);

    Requests returnRequest = reqController.putRequest(ID001, null);
    assertEquals(null, returnRequest);
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void employee_put_request_invalid_status() {
    dbReq.setStatus("PENDING");
    when(reqService.getRequestById(ID001)).thenReturn(dbReq);

    Requests returnRequest = reqController.putRequest(ID001, "BAD STATUS");
    assertEquals(null, returnRequest);
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void employee_put_request_valid_employee_status() {
    dbReq.setStatus("PENDING");
    when(reqService.getRequestById(ID001)).thenReturn(dbReq);

    req.setStatus("APPROVED");
    when(reqService.putRequest(ID001, "APPROVED")).thenReturn(req);

    Requests returnRequest = reqController.putRequest(ID001, "APPROVED");
    assertEquals(req, returnRequest);
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void employee_put_request_valid_employee_status_canceled() {
    dbReq.setStatus("PENDING");
    when(reqService.getRequestById(ID001)).thenReturn(dbReq);

    req.setStatus("CANCELED");
    when(reqService.putRequest(ID001, "CANCELED")).thenReturn(req);

    Requests returnRequest = reqController.putRequest(ID001, "CANCELED");
    assertEquals(req, returnRequest);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_put_request_request_null() {
    when(reqService.getRequestById(ID001)).thenReturn(null);

    Requests returnRequest = reqController.putRequest(ID001, "APPROVED");
    assertEquals(null, returnRequest);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_put_request_request_bad_status() {
    dbReq.setStatus("BAD STATUS");
    when(reqService.getRequestById(ID001)).thenReturn(dbReq);

    Requests returnRequest = reqController.putRequest(ID001, "APPROVED");
    assertEquals(null, returnRequest);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_put_request_invalid_user_made_request() {
    dbReq.setStatus("PENDING");
    dbReq.setUserid("BAD ID");
    when(reqService.getRequestById(ID001)).thenReturn(dbReq);

    Requests returnRequest = reqController.putRequest(ID001, "CANCELED");
    assertEquals(null, returnRequest);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_put_request_invalid_user_status() {
    dbReq.setStatus("PENDING");
    dbReq.setUserid("002");
    when(reqService.getRequestById(ID001)).thenReturn(dbReq);

    Requests returnRequest = reqController.putRequest(ID001, "BAD STATUS");
    assertEquals(null, returnRequest);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void user_put_request_all_good() {
    dbReq.setStatus("PENDING");
    dbReq.setUserid("002");
    when(reqService.getRequestById(ID001)).thenReturn(dbReq);

    req.setStatus("CANCELED");
    when(reqService.putRequest(ID001, "CANCELED")).thenReturn(req);
    Requests returnRequest = reqController.putRequest(ID001, "CANCELED");
    assertEquals(req, returnRequest);
  }
  
  @Test
  public void delete_request() {
    when(reqService.deleteRequest(ID001)).thenReturn(req);
    Requests returnedRequest = reqController.deleteRequest(ID001);
    assertEquals(req, returnedRequest);
  }
  
}