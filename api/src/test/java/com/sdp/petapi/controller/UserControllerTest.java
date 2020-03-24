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

@SpringBootTest
public class UserControllerTest {
  Pet pet;
  User employee, webUser;

  @Mock
  UserService userService;

  // makes a userService whose userDao is the mock above
  @InjectMocks
  UserController userContoller;

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
    when(userService.getAllUsers()).thenReturn(Arrays.asList(new User[] {employee, webUser}));
    List<User> list = userService.getAllUsers();
    assertEquals(Arrays.asList(new User[] {employee, webUser}), list);
  }
  
  @Test
  public void get_user_by_id() {
    String id = employee.getId();

    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getUserById(id)).thenReturn(employee);
    User actual_user = userContoller.getUserById(id);
    assertEquals(employee, actual_user);
  }

  @Test
  public void create_user() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    User new_user = new User(webUser.getEmail(), webUser.getPassHash(), webUser.getFirstName(), webUser.getLastName(), false);
    
    when(userService.createUser(new_user)).thenReturn(webUser);
    
    User returnUser = userContoller.createUser(new_user);
    assertEquals(webUser, returnUser);
  }

  @Test
  public void put_user() {
    webUser.setFirstName("Joe");
    assertEquals(webUser.getFirstName(), "Joe");

    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.putUser(webUser)).thenReturn(webUser);
    User returnedUser = userContoller.putUser(webUser.getId(), webUser);
    assertEquals(webUser, returnedUser);

    when(userService.getUserById(webUser.getId())).thenReturn(webUser);
    User updatedUser = userContoller.getUserById(webUser.getId());
    assertEquals(webUser, updatedUser);
  }

  @Test
  public void put_user_with_null_id_returns_null() {
    User webUser2 = new User(webUser.getEmail(), webUser.getPassHash(), webUser.getFirstName(), webUser.getLastName(), false);
    webUser2.setFirstName("Joe");
    assertEquals(webUser2.getFirstName(), "Joe");
    webUser2.setId(webUser.getId());
    assertEquals(webUser2.getId(), webUser.getId());

    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.putUser(webUser)).thenReturn(webUser);
    User returnedUser = userContoller.putUser(null, webUser2);
    assertNull(returnedUser);

    when(userService.getUserById(webUser.getId())).thenReturn(webUser);
    User updatedUser = userContoller.getUserById(webUser.getId());
    assertEquals(webUser, updatedUser);
  }

  @Test
  public void put_user_with_null_user_returns_null() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.putUser(webUser)).thenReturn(webUser);
    User returnedUser = userContoller.putUser(webUser.getId(), null);
    assertNull(returnedUser);

    when(userService.getUserById(webUser.getId())).thenReturn(webUser);
    User updatedUser = userContoller.getUserById(webUser.getId());
    assertEquals(webUser, updatedUser);
  }

  @Test
  public void put_user_with_wrong_id_returns_null() {
    webUser.setFirstName("Joe");
    assertEquals(webUser.getFirstName(), "Joe");

    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.putUser(webUser)).thenReturn(webUser);
    User returnedUser = userContoller.putUser(webUser.getId()+"1", webUser);
    assertNull(returnedUser);

    when(userService.getUserById(webUser.getId())).thenReturn(webUser);
    User updatedUser = userContoller.getUserById(webUser.getId());
    assertEquals(webUser, updatedUser);
  }

  @Test
  public void delete_pet() {
    // Since the userService is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getAllUsers()).thenReturn(Arrays.asList(new User[] {employee, webUser}));
    List<User> list = userContoller.getAllUser();
    assertEquals(Arrays.asList(new User[] {employee, webUser}), list);
    
    when(userService.deleteUser(webUser.getId())).thenReturn(webUser);
    User returnedUser = userContoller.deleteUser(webUser.getId());
    assertEquals(webUser, returnedUser);

    when(userService.getUserById(pet.getId())).thenReturn(null);
    User updatedUser = userContoller.getUserById(webUser.getId());
    assertNull(updatedUser);
  }

  @Test
  public void add_pet_by_id_to_webUser_favorites_list() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getFavoritePets(webUser.getId())).thenReturn(null);
    List<Pet> list = userContoller.getFavoritePets(webUser.getId());
    assertNull(list);
    
    when(userService.addPetToFavorites(webUser, pet.getId())).thenReturn(true);
    Boolean result = userContoller.addPetToFavorites(pet.getId(), webUser);
    assertTrue(result);

    when(userService.getFavoritePets(webUser.getId())).thenReturn(Collections.singletonList(pet));
    List<Pet> updated_list = userContoller.getFavoritePets(webUser.getId());
    assertEquals(updated_list, Collections.singletonList(pet));
  }

  @Test
  public void remove_pet_by_id_to_webUser_favorites_list() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getFavoritePets(webUser.getId())).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userContoller.getFavoritePets(webUser.getId());
    assertEquals(list, Collections.singletonList(pet));
    
    when(userService.removePetFromFavorites(webUser, pet.getId())).thenReturn(true);
    Boolean result = userContoller.removePetFromFavorites(pet.getId(), webUser);
    assertTrue(result);

    when(userService.getFavoritePets(webUser.getId())).thenReturn(null);
    List<Pet> updated_list = userContoller.getFavoritePets(webUser.getId());
    assertNull(updated_list);
  }

  @Test
  public void add_pet_by_id_to_webUser_recently_visited_list() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getRecentPets(webUser.getId())).thenReturn(null);
    List<Pet> list = userContoller.getRecentPets(webUser.getId());
    assertNull(list);
    
    when(userService.addPetToRecents(webUser, pet.getId())).thenReturn(true);
    Boolean result = userContoller.addPetToRecents(pet.getId(), webUser);
    assertTrue(result);

    when(userService.getFavoritePets(webUser.getId())).thenReturn(Collections.singletonList(pet));
    List<Pet> updated_list = userContoller.getFavoritePets(webUser.getId());
    assertEquals(updated_list, Collections.singletonList(pet));
  }

  @Test
  public void get_webUser_favorites_list() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getFavoritePets(webUser.getId())).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userContoller.getFavoritePets(webUser.getId());
    assertEquals(list, Collections.singletonList(pet));
  }

  @Test
  public void get_webUser_recently_visited_list() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userService.getRecentPets(webUser.getId())).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userContoller.getRecentPets(webUser.getId());
    assertEquals(list, Collections.singletonList(pet));
  }
  
}