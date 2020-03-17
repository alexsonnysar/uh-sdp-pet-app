package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.controllers.UserController;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Requested;
import com.sdp.petapi.models.User;
import com.sdp.petapi.services.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest {

  User user;
  Requested request;

  @Mock
  UserService userService;

  // makes a user controller whose userService is the mock above
  @InjectMocks
  UserController userController;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    user = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/userObject.json"), User.class);
    request = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/requestedObject.json"), Requested.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_all_users() {
    // Since the petService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));
    List<User> list = userController.getAllUsers();
    assertEquals(Collections.singletonList(user), list);
  }

  @Test
  public void get_user_by_id() {
    when(userService.getUserById(user.getId())).thenReturn(user);
    User returnUser = userController.getUserById(user.getId());
    assertEquals(user, returnUser);
  }

  @Test
  public void create_user() {
    when(userService.createUser(user)).thenReturn(user);
    User returnUser = userController.createUser(user);
    assertEquals(user, returnUser);
  }

  @Test
  public void put_user() {
    when(userService.putUser(user)).thenReturn(user);
    Message returnMessage = userController.putUser(user.getId(), user);
    assertEquals("Updated User", returnMessage.getMessage());
  }

  @Test
  public void put_user_returns_null() {
    when(userService.putUser(user)).thenReturn(null);
    Message returnMessage = userController.putUser(user.getId(), user);
    assertEquals("Couldn't update User", returnMessage.getMessage());
  }

  @Test
  public void delete_user() {
    when(userService.deleteUser(user.getId())).thenReturn(true);
    Message returnMessage = userController.deleteUser(user.getId());
    assertEquals("deleted User", returnMessage.getMessage());
  }

  @Test
  public void delete_user_returns_false() {
    when(userService.deleteUser(user.getId())).thenReturn(false);
    Message returnMessage = userController.deleteUser(user.getId());
    assertEquals("Couldn't delete User", returnMessage.getMessage());
  }

  @Test
  public void request_adoption() {
    when(userService.requestAdoption(request)).thenReturn(request);
    Requested adoptionRequestSuccess = userController.requestAdoption(request);
    assertEquals(request, adoptionRequestSuccess);
  }

  

}