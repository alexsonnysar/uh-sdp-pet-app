package com.sdp.petapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.dao.RequestsDao;
import com.sdp.petapi.models.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestsServiceTest {
  User employee, webUser;
  Requests req, req2;

  @Mock
  RequestsDao reqDao;

  // makes a reqService whose reqDao is the mock above
  @InjectMocks
  RequestsService reqService;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
    webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
    req = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject.json"), Requests.class);
    req2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestObject2.json"), Requests.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_all_requests() {
    // Since the reqDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqDao.getAllRequests()).thenReturn(Arrays.asList(new Requests[] {req, req2}));
    List<Requests> list = reqService.getAllRequests();
    assertEquals(Arrays.asList(new Requests[] {req, req2}), list);
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
    Requests new_req = new Requests("002", "002");
    when(reqDao.createRequest(webUser, "002")).thenReturn(new_req);
    Requests returnRequest = reqService.createRequest(webUser, "002");
    assertEquals(new_req, returnRequest);
  }

  @Test
  public void put_request() {
    // Since the reqDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(reqDao.putRequests(employee, req)).thenReturn(req);
    Requests returnedRequest = reqService.putRequest(employee, req);
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