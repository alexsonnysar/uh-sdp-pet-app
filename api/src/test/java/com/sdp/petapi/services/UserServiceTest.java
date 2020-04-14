package com.sdp.petapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.dao.UserDao;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
  transient Pet pet;
  transient User employee, webUser;

  @Mock
  transient UserDao userDao;

  @InjectMocks
  transient UserService userService;

  private static final String ID001 = "001";
  private static final String ID002 = "002";

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
    when(userDao.getAllUsers()).thenReturn(Arrays.asList(new User[] {employee, webUser}));
    List<User> list = userService.getAllUsers();
    assertEquals(Arrays.asList(new User[] {employee, webUser}), list);
  }
  @Test
  public void get_user_by_id() {
    when(userDao.getUserById(ID001)).thenReturn(employee);
    User actual_user = userService.getUserById(ID001);
    assertEquals(employee, actual_user);
  }

  @Test
  public void create_user() {
    when(userDao.createUser(webUser)).thenReturn(webUser);
    User returnUser = userService.createUser(webUser);
    assertEquals(webUser, returnUser);
  }

  @Test
  public void put_user() {
    when(userDao.putUser(webUser)).thenReturn(webUser);
    User returnedUser = userService.putUser(webUser);
    assertEquals(webUser, returnedUser);
  }

  @Test
  public void delete_user() {
    when(userDao.deleteUser(ID002)).thenReturn(webUser);
    User returnedUser = userService.deleteUser(ID002);
    assertEquals(webUser, returnedUser);
  }

  @Test
  public void add_pet_by_id_to_user_favorites_list() {
    when(userDao.addPetToFavorites(ID002, ID001)).thenReturn(true);
    Boolean result = userService.addPetToFavorites(ID002, ID001);
    assertTrue(result);
  }

  @Test
  public void remove_pet_by_id_from_user_favorites_list() {
    when(userDao.removePetFromFavorites(ID002, ID001)).thenReturn(true);
    Boolean result = userService.removePetFromFavorites(ID002, ID001);
    assertTrue(result);
  }

  @Test
  public void add_pet_by_id_to_user_recently_visited_list() {
    when(userDao.addPetToRecents(ID002, ID001)).thenReturn(true);
    Boolean result = userService.addPetToRecents(ID002, ID001);
    assertTrue(result);
  }

  @Test
  public void get_user_favorites_list() {
    when(userDao.getFavoritePets(ID002)).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userService.getFavoritePets(ID002);
    assertEquals(list, Collections.singletonList(pet));
  }

  @Test
  public void get_user_recently_visited_list() {
    when(userDao.getRecentPets(ID002)).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userService.getRecentPets(ID002);
    assertEquals(list, Collections.singletonList(pet));
  }

  @Test
  public void user_exists_by_email() {
    when(userDao.existsByEmail("1234@mail.com")).thenReturn(true);
    Boolean resp = userService.existsByEmail("1234@mail.com");
    assertTrue(resp);
  }

  @Test
  public void get_user_by_email() {
    when(userDao.getUserByEmail("ironman@mail.com")).thenReturn(webUser);
    User user = userService.getUserByEmail("ironman@mail.com");
    assertEquals(user, webUser);
  }
}