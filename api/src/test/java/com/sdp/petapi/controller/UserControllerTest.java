package com.sdp.petapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.services.UserService;
import com.sdp.petapi.controllers.UserController;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
class UserControllerTest {
  Pet pet;
  User employee, webUser;

  @Mock
  UserService userService;

  // makes a userController whose userService is the mock above
  @InjectMocks
  UserController userController;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);

    employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);

    webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_all_users() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getAllUsers()).thenReturn(Arrays.asList(new User[] { employee, webUser }));
    List<User> list = userController.getAllUser();
    assertEquals(Arrays.asList(new User[] { employee, webUser }), list);
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService")
  public void get_user_by_id_employee_gets_himself() {
    String id = "001";

    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getUserById("001")).thenReturn(employee);
    when(userService.getUserById("002")).thenReturn(webUser);
    User actual_user = userController.getUserById(id);
    assertEquals(employee, actual_user);
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService")
  public void get_user_by_id_employee_gets_other() {
    String id = "002";

    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getUserById("001")).thenReturn(employee);
    when(userService.getUserById("002")).thenReturn(webUser);
    User actual_user = userController.getUserById(id);
    assertEquals(webUser, actual_user);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService")
  public void get_user_by_id_user_gets_himself() {
    String id = "002";

    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getUserById("001")).thenReturn(employee);
    when(userService.getUserById("002")).thenReturn(webUser);
    User actual_user = userController.getUserById(id);
    assertEquals(webUser, actual_user);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService")
  public void get_user_by_id_user_gets_other() {
    String id = "001";

    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getUserById("001")).thenReturn(employee);
    when(userService.getUserById("002")).thenReturn(webUser);
    User actual_user = userController.getUserById(id);
    assertEquals(webUser, actual_user);
  }


  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService")
  public void put_user() {
    when(userService.putUser(webUser)).thenReturn(webUser);
    User returnedUser = userController.putUser(webUser);
    assertEquals(webUser, returnedUser);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService")
  public void put_user_with_null_id_returns_null() {
    User nullIdUser = webUser;
    nullIdUser.setId(null);

    when(userService.putUser(webUser)).thenReturn(webUser);
    User returnedUser = userController.putUser(nullIdUser);
    assertEquals(webUser, returnedUser);
  }

  @Test
  public void delete_pet() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.deleteUser("002")).thenReturn(webUser);
    User returnedUser = userController.deleteUser("002");
    assertEquals(webUser, returnedUser);
  }

  @Test
  public void add_pet_by_id_to_user_favorites_list() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.addPetToFavorites(webUser, "001")).thenReturn(true);
    Boolean result = userController.addPetToFavorites("001", webUser);
    assertTrue(result);
  }

  @Test
  public void remove_pet_by_id_from_user_favorites_list() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.removePetFromFavorites(webUser, "001")).thenReturn(true);
    Boolean result = userController.removePetFromFavorites("001", webUser);
    assertTrue(result);
  }

  @Test
  public void add_pet_by_id_to_user_recently_visited_list() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.addPetToRecents(webUser, "001")).thenReturn(true);
    Boolean result = userController.addPetToRecents("001", webUser);
    assertTrue(result);
  }

  @Test
  public void get_user_favorites_list() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getFavoritePets("002")).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userController.getFavoritePets("002");
    assertEquals(list, Collections.singletonList(pet));
  }

  @Test
  public void get_user_recently_visited_list() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getRecentPets("002")).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userController.getRecentPets("002");
    assertEquals(list, Collections.singletonList(pet));
  }

}