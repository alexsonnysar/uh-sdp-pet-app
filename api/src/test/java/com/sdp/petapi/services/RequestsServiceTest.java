package com.sdp.petapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.dao.RequestsDao;
import com.sdp.petapi.models.Requests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestsServiceTest {
  Requests req;

  @Mock
  RequestsDao reqDao;

  // makes a reqService whose reqDao is the mock above
  @InjectMocks
  RequestsService reqService;

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
    // Since the reqDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqDao.getAllRequests()).thenReturn(Collections.singletonList(req));
    List<Requests> list = reqService.getAllRequests();
    assertEquals(Collections.singletonList(req), list);
  }
  @Test
  public void get_request_by_id() {
    String id = "001";

    // Since the reqDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqDao.getRequestById(id)).thenReturn(req);
    Requests actual_request = reqService.getRequestById(id);
    assertEquals(req, actual_request);
  }

  @Test
  public void create_request() {
    // Since the reqDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqDao.createRequest(req)).thenReturn(req);
    Requests returnRequest = reqService.createRequest(req);
    assertEquals(req, returnRequest);
  }

  @Test
  public void put_request() {
    // Since the reqDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqDao.putRequests(req)).thenReturn(req);
    Requests returnedRequest = reqService.putRequest(req);
    assertEquals(req, returnedRequest);
  }

  @Test
  public void delete_request() {
    // Since the reqDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqDao.deleteRequest("001")).thenReturn(req);
    Requests returnedRequest = reqService.deleteRequest("001");
    assertEquals(req, returnedRequest);
  }
}