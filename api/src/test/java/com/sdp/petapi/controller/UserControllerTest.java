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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

@SpringBootTest
class UserControllerTest {

  private static final String EMPLOYEE_ID_STRING = "001";
  private static final String USER_ID_STRING     = "002";
  private static final String PET_ID_STRING      = "001";

  transient Pet pet;
  transient User employee, webUser;

  @Mock
  transient UserService userService;

  // makes a userController whose userService is the mock above
  @InjectMocks
  transient UserController userController;



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
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void get_user_by_id_employee_gets_himself() {
    when(userService.getUserById(EMPLOYEE_ID_STRING)).thenReturn(employee);
    when(userService.getUserById(USER_ID_STRING)).thenReturn(webUser);
    User actual_user = userController.getUserById(EMPLOYEE_ID_STRING);
    assertEquals(employee, actual_user);
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void get_user_by_id_employee_gets_other() {
    when(userService.getUserById(EMPLOYEE_ID_STRING)).thenReturn(employee);
    when(userService.getUserById(USER_ID_STRING)).thenReturn(webUser);
    User actual_user = userController.getUserById(USER_ID_STRING);
    assertEquals(webUser, actual_user);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void get_user_by_id_user_gets_himself() {
    when(userService.getUserById(EMPLOYEE_ID_STRING)).thenReturn(employee);
    when(userService.getUserById(USER_ID_STRING)).thenReturn(webUser);
    User actual_user = userController.getUserById(USER_ID_STRING);
    assertEquals(webUser, actual_user);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void get_user_by_id_user_gets_other() {
    when(userService.getUserById(EMPLOYEE_ID_STRING)).thenReturn(employee);
    when(userService.getUserById(USER_ID_STRING)).thenReturn(webUser);
    User actual_user = userController.getUserById(EMPLOYEE_ID_STRING);
    assertEquals(webUser, actual_user);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void put_user() {
    when(userService.putUser(webUser)).thenReturn(webUser);
    User returnedUser = userController.putUser(webUser);
    assertEquals(webUser, returnedUser);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void put_user_with_null_id_returns_null() {
    User nullIdUser = webUser;
    nullIdUser.setId(null);

    when(userService.putUser(webUser)).thenReturn(webUser);
    User returnedUser = userController.putUser(nullIdUser);
    assertEquals(webUser, returnedUser);
  }

  @Test
  public void delete_user() {
    when(userService.deleteUser(USER_ID_STRING)).thenReturn(webUser);
    User returnedUser = userController.deleteUser(USER_ID_STRING);
    assertEquals(webUser, returnedUser);
  }

  @Test
  public void add_pet_by_id_to_user_favorites_list() {
    when(userService.addPetToFavorites(webUser, PET_ID_STRING)).thenReturn(true);
    Boolean result = userController.addPetToFavorites(PET_ID_STRING, webUser);
    assertTrue(result);
  }

  @Test
  public void remove_pet_by_id_from_user_favorites_list() {
    when(userService.removePetFromFavorites(webUser, PET_ID_STRING)).thenReturn(true);
    Boolean result = userController.removePetFromFavorites(PET_ID_STRING, webUser);
    assertTrue(result);
  }

  @Test
  public void add_pet_by_id_to_user_recently_visited_list() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.addPetToRecents(webUser, PET_ID_STRING)).thenReturn(true);
    Boolean result = userController.addPetToRecents(PET_ID_STRING, webUser);
    assertTrue(result);
  }

  @Test
  public void get_user_favorites_list() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getFavoritePets(USER_ID_STRING)).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userController.getFavoritePets(USER_ID_STRING);
    assertEquals(list, Collections.singletonList(pet));
  }

  @Test
  public void get_user_recently_visited_list() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getRecentPets(USER_ID_STRING)).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userController.getRecentPets(USER_ID_STRING);
    assertEquals(list, Collections.singletonList(pet));
  }

  @Test
  public void user_exists_by_email() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.existsByEmail("1234@mail.com")).thenReturn(true);
    Boolean resp = userController.existsByEmail("1234@mail.com");
    assertTrue(resp);
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void get_user_by_email_employee_gets_himself() {
    when(userService.getUserByEmail("1234@mail.com")).thenReturn(employee);
    when(userService.getUserByEmail("ironman@mail.com")).thenReturn(webUser);
    User actual_user = userController.getUserByEmail("1234@mail.com");
    assertEquals(employee, actual_user);
  }

  @Test
  @WithUserDetails(value = "Employee", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void get_user_by_email_employee_gets_other() {
    when(userService.getUserByEmail("1234@mail.com")).thenReturn(employee);
    when(userService.getUserByEmail("ironman@mail.com")).thenReturn(webUser);
    User actual_user = userController.getUserByEmail("ironman@mail.com");
    assertEquals(webUser, actual_user);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void get_user_by_email_user_gets_himself() {
    when(userService.getUserByEmail("1234@mail.com")).thenReturn(employee);
    when(userService.getUserByEmail("ironman@mail.com")).thenReturn(webUser);
    User actual_user = userController.getUserByEmail("ironman@mail.com");
    assertEquals(webUser, actual_user);
  }

  @Test
  @WithUserDetails(value = "User", userDetailsServiceBeanName = "TestingUserDetailsService") //NOPMD
  public void get_user_by_email_user_gets_other() {
    when(userService.getUserByEmail("1234@mail.com")).thenReturn(employee);
    when(userService.getUserByEmail("ironman@mail.com")).thenReturn(webUser);
    User actual_user = userController.getUserByEmail("1234@mail.com");
    assertEquals(webUser, actual_user);
  }

}