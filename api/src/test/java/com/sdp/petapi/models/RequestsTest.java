package com.sdp.petapi.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

@SpringBootTest
class RequestsTest {

  @Test
  public void createTwoArgs() throws Exception {
    Date current = new Date();
    Requests sample_request = new Requests("jacob", "juana");
    assertNotNull(sample_request);
    assertNull(sample_request.getId());
    assertEquals(sample_request.getUserid(), "jacob");
    assertEquals(sample_request.getPetid(), "juana");
    assertNotNull(sample_request.getRequestDate());
    assertTrue(sample_request.getRequestDate().after(current) || sample_request.getRequestDate().equals(current));
    assertTrue(sample_request.getRequestDate().before(new Date()) || sample_request.getRequestDate().equals(new Date()));
  }

}
