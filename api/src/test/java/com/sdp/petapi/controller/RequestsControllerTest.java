package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.services.RequestsService;
import com.sdp.petapi.controllers.RequestsController;
import com.sdp.petapi.models.Requests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestsControllerTest {
  Requests req;

  @Mock
  RequestsService reqService;

  // makes a reqController whose reqService is the mock above
  @InjectMocks
  RequestsController reqController;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    req = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject.json"), Requests.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_all_requests() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.getAllRequests()).thenReturn(Collections.singletonList(req));
    List<Requests> list = reqController.getAllRequests();
    assertEquals(Collections.singletonList(req), list);
  }
  
  @Test
  public void get_request_by_id() {
    String id = "001";

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
    when(reqService.createRequest(req)).thenReturn(req);
    Requests returnRequest = reqController.createRequest(req);
    assertEquals(req, returnRequest);
  }

  @Test
  public void put_request() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.putRequest(req)).thenReturn(req);
    Requests returnedRequest = reqController.putRequest("001", req);
    assertEquals(req, returnedRequest);
  }

  @Test
  public void put_request_with_null_id_return_null() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.putRequest(req)).thenReturn(req);
    Requests returnedRequest = reqController.putRequest(null, req);
    assertNull(returnedRequest);
  }

  @Test
  public void put_request_with_incorrect_id_returns_null() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.putRequest(req)).thenReturn(req);
    Requests returnedRequest = reqController.putRequest("010", req);
    assertNull(returnedRequest);
  }

  @Test
  public void delete_request() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqService.deleteRequest("001")).thenReturn(req);
    Requests returnedRequest = reqController.deleteRequest("001");
    assertEquals(req, returnedRequest);
  }
  
}