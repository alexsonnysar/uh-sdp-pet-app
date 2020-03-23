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
public class UserServiceTest {
  Pet pet, pet2;
  User employee, webUser;

  @Mock
  UserDao userDao;

  // makes a userService whose userDao is the mock above
  @InjectMocks
  UserService userService;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    
    pet2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject2.json"), Pet.class);
    
    employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
    
    webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_all_users() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userDao.getAllUsers()).thenReturn(Arrays.asList(new User[] {employee, webUser}));
    List<User> list = userService.getAllUsers();
    assertEquals(Arrays.asList(new User[] {employee, webUser}), list);
  }
  
  @Test
  public void get_user_by_id() {
    String id = employee.getId();

    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userDao.getUserById(id)).thenReturn(employee);
    User actual_user = userService.getUserById(id);
    assertEquals(employee, actual_user);
  }

  @Test
  public void create_user() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    User new_user = new User(webUser.getEmail(), webUser.getPassHash(), webUser.getFirstName(), webUser.getLastName(), false);
    
    when(userDao.createUser(new_user)).thenReturn(webUser);
    
    User returnUser = userService.createUser(new_user);
    assertEquals(webUser, returnUser);
  }

  @Test
  public void put_pet() {
    webUser.setFirstName("Changed Pet");
    assertEquals(webUser.getFirstName(), "Changed Pet");

    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userDao.putUser(webUser)).thenReturn(webUser);
    User returnedUser = userService.putUser(webUser);
    assertEquals(webUser, returnedUser);

    when(userDao.getUserById(webUser.getId())).thenReturn(webUser);
    User updatedUser = userService.getUserById(webUser.getId());
    assertEquals(webUser, updatedUser);
  }

  @Test
  public void delete_pet() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userDao.getAllUsers()).thenReturn(Arrays.asList(new User[] {employee, webUser}));
    List<User> list = userService.getAllUsers();
    assertEquals(Arrays.asList(new User[] {employee, webUser}), list);
    
    when(userDao.deleteUser(webUser.getId())).thenReturn(webUser);
    User returnedUser = userService.deleteUser(webUser.getId());
    assertEquals(webUser, returnedUser);

    when(userDao.getUserById(pet.getId())).thenReturn(null);
    User updatedUser = userService.getUserById(webUser.getId());
    assertNull(updatedUser);
  }

  @Test
  public void add_pet_by_id_to_webUser_favorites_list() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userDao.getFavoritePets(webUser)).thenReturn(null);
    List<Pet> list = userService.getFavoritePets(webUser);
    assertNull(list);
    
    when(userDao.addPetToFavorites(webUser, pet.getId())).thenReturn(true);
    Boolean result = userService.addPetToFavorites(webUser, pet.getId());
    assertTrue(result);

    when(userDao.getFavoritePets(webUser)).thenReturn(Collections.singletonList(pet));
    List<Pet> updated_list = userService.getFavoritePets(webUser);
    assertEquals(updated_list, Collections.singletonList(pet));
  }

  @Test
  public void remove_pet_by_id_to_webUser_favorites_list() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userDao.getFavoritePets(webUser)).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userService.getFavoritePets(webUser);
    assertEquals(list, Collections.singletonList(pet));
    
    when(userDao.removePetFromFavorites(webUser, pet.getId())).thenReturn(true);
    Boolean result = userService.removePetFromFavorites(webUser, pet.getId());
    assertTrue(result);

    when(userDao.getFavoritePets(webUser)).thenReturn(null);
    List<Pet> updated_list = userService.getFavoritePets(webUser);
    assertNull(updated_list);
  }

  @Test
  public void add_pet_by_id_to_webUser_recently_visited_list() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userDao.getRecentPets(webUser)).thenReturn(null);
    List<Pet> list = userService.getRecentPets(webUser);
    assertNull(list);
    
    when(userDao.addPetToFavorites(webUser, pet.getId())).thenReturn(true);
    Boolean result = userService.addPetToFavorites(webUser, pet.getId());
    assertTrue(result);

    when(userDao.getFavoritePets(webUser)).thenReturn(Collections.singletonList(pet));
    List<Pet> updated_list = userService.getFavoritePets(webUser);
    assertEquals(updated_list, Collections.singletonList(pet));
  }

  @Test
  public void get_webUser_favorites_list() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userDao.getFavoritePets(webUser)).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userService.getFavoritePets(webUser);
    assertEquals(list, Collections.singletonList(pet));
  }

  @Test
  public void get_webUser_recently_visited_list() {
    // Since the petDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userDao.getRecentPets(webUser)).thenReturn(Collections.singletonList(pet));
    List<Pet> list = userService.getRecentPets(webUser);
    assertEquals(list, Collections.singletonList(pet));
  }
  
}