package com.sdp.petapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.dao.RequestsDao;
import com.sdp.petapi.models.RequestInformation;
import com.sdp.petapi.models.Requests;
import com.sdp.petapi.models.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestsServiceTest {

  private static final String ID001 = "001";
  private static final String ID002 = "002";
  private static final String DATEFORMAT = "dd-MMM-yyyy HH:mm:ss";
  private static final String FEBDATE = "26-FEB-2020 18:16:17";

  transient Requests req;
  transient User employee, webUser, webUser2;
  transient RequestInformation reqInfo;
  transient List<RequestInformation> reqInfoList;

  @Mock
  transient RequestsDao reqDao;


  // makes a reqService whose reqDao is the mock above
  @InjectMocks
  transient RequestsService reqService;

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
    when(reqDao.getAllRequests()).thenReturn(Collections.singletonList(req));
    List<Requests> list = reqService.getAllRequests();
    assertEquals(Collections.singletonList(req), list);
  }
  @Test
  public void get_request_by_id() {
    String id = "001";
    when(reqDao.getRequestById(id)).thenReturn(req);
    Requests actual_request = reqService.getRequestById(id);
    assertEquals(req, actual_request);
  }

  @Test
  public void create_request() {
    when(reqDao.createRequest(ID002, ID001)).thenReturn(req);
    Requests returnRequest = reqService.createRequest(ID002, ID001);
    assertEquals(req, returnRequest);
  }

  @Test
  public void put_request() {
    when(reqDao.putRequests(ID001, "APPROVED")).thenReturn(req);
    Requests returnedRequest = reqService.putRequest(ID001, "APPROVED");
    assertEquals(req, returnedRequest);
  }

  @Test
  public void delete_request() {
    when(reqDao.deleteRequest("001")).thenReturn(req);
    Requests returnedRequest = reqService.deleteRequest("001");
    assertEquals(req, returnedRequest);
  }

  @Test
  public void get_all_request_info() throws Exception {
    reqInfoList = Collections.singletonList(new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING"));
    when(reqDao.getAllRequestInfo()).thenReturn(reqInfoList);
    List<RequestInformation> list = reqService.getAllRequestInfo();
    assertEquals(reqInfoList, list);
  }
  @Test
  public void get_request_info_by_id() throws Exception {
    reqInfo = new RequestInformation(ID001, ID002, "Tony Stark", "ironman@mail.com", ID001, "Buddy", "N/A", new SimpleDateFormat(DATEFORMAT, new Locale("en")).parse(FEBDATE), "PENDING");
    when(reqDao.getRequestInfoById(ID001)).thenReturn(reqInfo);
    RequestInformation actual_info = reqService.getRequestInfoById(ID001);
    assertEquals(reqInfo, actual_info);
  }
}