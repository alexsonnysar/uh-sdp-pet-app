package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.User;
import com.sdp.petapi.repositories.PetRepository;
import com.sdp.petapi.repositories.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoTest {

  @Autowired
  UserDao userDao;

  @Autowired
  PetRepository petRepository;

  @Autowired
  UserRepository userRepository;

  Pet pet, pet2;
  User employee, webUser;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    pet = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject.json"), Pet.class);
    petRepository.insert(pet);

    pet2 = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/petObject2.json"), Pet.class);
    petRepository.insert(pet2);
    
    employee = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/employeeObject.json"), User.class);
    userRepository.insert(employee);
    webUser = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/webUserObject.json"), User.class);
    userRepository.insert(webUser);
  }

  @AfterEach
  public void cleanup() {
    petRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  public void get_all_users() {
    List<User> actual_users = userDao.getAllUsers();
    assertEquals(Arrays.asList(new User[]{employee, webUser}), actual_users);
  }

  @Test
  public void get_good_user_by_id_returns_user() {
    String id = "002";
    User actual_user = userDao.getUserById(id);
    assertEquals(webUser, actual_user);
  }

  @Test
  public void get_bad_pet_by_id_returns_null() {
    String id = "010";
    User actual_user = userDao.getUserById(id);
    assertNull(actual_user);
  }

  @Test
  public void get_pet_by_null_id_returns_null() {
    User actual_user = userDao.getUserById(null);
    assertNull(actual_user);
  }

  @Test
  public void create_user() {
    webUser.setId(null);
    assertNull(webUser.getId());

    List<User> orig_user_list = userDao.getAllUsers();

    User inserted_user = userDao.createUser(webUser);
    List<User> updated_user_list = userDao.getAllUsers();

    assertAll(
      () -> assertNotNull(inserted_user),
      () -> assertNotNull(inserted_user.getId()),
      () -> assertEquals(updated_user_list.size(), orig_user_list.size() + 1),
      () -> assertFalse(orig_user_list.contains(inserted_user)),
      () -> assertTrue(updated_user_list.contains(inserted_user))
    );
  }

  @Test
  public void create_user_with_null_user_returns_null() {
    List<User> orig_user_list = userDao.getAllUsers();

    User inserted_user = userDao.createUser(null);
    assertAll(
      () -> assertNull(inserted_user),
      () -> assertEquals(userDao.getAllUsers(), orig_user_list)
    );
  }

  @Test
  public void create_user_with_id_returns_null() {
    webUser.setId("003");
    assertEquals(webUser.getId(), "003");
    List<User> orig_user_list = userDao.getAllUsers();

    User inserted_user = userDao.createUser(webUser);
    assertAll(
      () -> assertNull(inserted_user),
      () -> assertEquals(userDao.getAllUsers(), orig_user_list)
    );
  }

  @Test
  public void put_user() {
    webUser.setName("Aymen");
    assertEquals(webUser.getName(), "Aymen");

    List<User> orig_user_list = userDao.getAllUsers();

    User updated_user = userDao.putUser(webUser);
    List<User> updated_user_list = userDao.getAllUsers();
    assertAll(
      () -> assertEquals(webUser, updated_user),
      () -> assertEquals(updated_user_list.size(), orig_user_list.size()),
      () -> assertFalse(orig_user_list.contains(webUser)),
      () -> assertTrue(updated_user_list.contains(webUser))
    );
  }

  @Test
  public void put_user_with_null_user_returns_null() {
    webUser.setName("Aymen");
    assertEquals(webUser.getName(), "Aymen");

    List<User> orig_user_list = userDao.getAllUsers();

    User updated_user = userDao.putUser(null);
    List<User> updated_user_list = userDao.getAllUsers();

    assertAll(
      () -> assertNull(updated_user),
      () -> assertEquals(updated_user_list, orig_user_list)
    );
  }

  // @Test
  // public void put_user_with_bad_id_returns_null() {
  //   webUser.setName("Aymen");
  //   assertEquals(webUser.getName(), "Aymen");
  //   webUser.setId(null);
  //   assertNull(webUser.getId());

  //   List<User> orig_user_list = userDao.getAllUsers();

  //   User updated_user = userDao.putUser(webUser);
  //   List<User> updated_user_list = userDao.getAllUsers();

  //   assertAll(
  //     () -> assertNull(updated_user),
  //     () -> assertEquals(updated_user_list, orig_user_list)
  //   );
  // }

  @Test
  public void delete_user() {
    String id = "002";

    List<User> orig_user_list = userDao.getAllUsers();
    User deleted_user = userDao.deleteUser(id);
    List<User> updated_user_list = userDao.getAllUsers();

    assertAll(
      () -> assertEquals(webUser, deleted_user),
      () -> assertTrue(orig_user_list.contains(webUser)),
      () -> assertFalse(updated_user_list.contains(webUser)),
      () -> assertEquals(orig_user_list.size(), updated_user_list.size() + 1)
    );
  }

  @Test
  public void delete_user_with_null_id_returns_null() {
    List<User> orig_user_list = userDao.getAllUsers();
    User deleted_user = userDao.deleteUser(null);
    List<User> updated_user_list = userDao.getAllUsers();

    assertAll(
      () -> assertNull(deleted_user),
      () -> assertEquals(orig_user_list, updated_user_list)
    );
  }

  @Test
  public void delete_nonexistent_user_returns_null() {
    String id = "010";

    List<User> orig_user_list = userDao.getAllUsers();
    User deleted_user = userDao.deleteUser(id);
    List<User> updated_user_list = userDao.getAllUsers();

    assertAll(
      () -> assertNull(deleted_user),
      () -> assertEquals(orig_user_list, updated_user_list)
    );
  }

  @Test
  public void add_pet_to_webUser_favorite_list() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertTrue(userDao.addPetToFavorites(webUser, "001"));
    assertArrayEquals(webUser.getFavorites(), new String[] {"001"});

    User userdb = userDao.getUserById("002");
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_pet_to_null_user_favorite_list_returns_false() {
    assertFalse(userDao.addPetToFavorites(null, "001"));
  }

  @Test
  public void add_pet_to_employee_favorite_list_returns_false() {
    assertArrayEquals(employee.getFavorites(), new String[0]);
    assertFalse(userDao.addPetToFavorites(employee, "001"));
    assertArrayEquals(employee.getFavorites(), new String[0]);
    User userdb = userDao.getUserById("001");
    assertEquals(employee, userdb);
  }

  @Test
  public void add_pet_to_bad_webUser_favorite_list_returns_false() {
    webUser.setName("Doe");
    assertEquals(webUser.getName(), "Doe");
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToFavorites(webUser, "001"));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById("002");
    webUser.setName("Tony Stark");
    assertEquals(webUser.getName(), "Tony Stark");
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_null_pet_to_webUser_favorite_list_returns_false() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToFavorites(webUser, null));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById("002");
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_inactive_pet_to_webUser_favorite_list_returns_false() {
    pet.setActive(false);
    Pet updated_pet = petRepository.save(pet);
    assertEquals(pet, updated_pet);

    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToFavorites(webUser, "001"));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById("002");
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_duplicate_pet_to_webUser_favorite_list() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    userDao.addPetToFavorites(webUser, "001");
    assertArrayEquals(webUser.getFavorites(), new String[] {"001"});

    assertFalse(userDao.addPetToFavorites(webUser, "001"));
    assertArrayEquals(webUser.getFavorites(), new String[] {"001"});

    User userdb = userDao.getUserById("002");
    assertEquals(webUser, userdb);
  }

  @Test
  public void remove_pet_from_webUser_favorite_list() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    userDao.addPetToFavorites(webUser, "001");
    assertArrayEquals(webUser.getFavorites(), new String[] {"001"});
    User userdb = userDao.getUserById("002");
    assertEquals(webUser, userdb);

    assertTrue(userDao.removePetFromFavorites(webUser, "001"));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User updated_user = userDao.getUserById("002");
    assertEquals(webUser, updated_user);
  }

  @Test
  public void remove_pet_from_null_webUser_favorite_list_returns_false() {
    assertFalse(userDao.removePetFromFavorites(null, "001"));
  }

  @Test
  public void remove_pet_from_employee_favorite_list_returns_false() {
    assertArrayEquals(employee.getFavorites(), new String[0]);

    assertFalse(userDao.removePetFromFavorites(employee, "001"));
    assertArrayEquals(employee.getFavorites(), new String[0]);
    User updated_user = userDao.getUserById("001");
    assertEquals(employee, updated_user);
  }

  @Test
  public void remove_pet_from_bad_webUser_favorite_list_returns_false() {
    webUser.setName("Davis");
    assertEquals(webUser.getName(), "Davis");

    assertFalse(userDao.removePetFromFavorites(webUser, "001"));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User updated_user = userDao.getUserById("002");
    webUser.setName("Tony Stark");
    assertEquals(webUser.getName(), "Tony Stark");

    assertEquals(webUser, updated_user);
  }

  @Test
  public void remove_nonexistent_petid_from_webUser_favorite_list_returns_false() {
    assertFalse(userDao.removePetFromFavorites(webUser, null));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User updated_user = userDao.getUserById("002");
    assertEquals(webUser, updated_user);
  }

  @Test
  public void add_pet_to_webUser_recent_list() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertTrue(userDao.addPetToRecents(webUser, "001"));
    assertArrayEquals(webUser.getRecents(), new String[] {"001"});

    User userdb = userDao.getUserById("002");
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_pet_to_null_user_recent_list_returns_false() {
    assertFalse(userDao.addPetToRecents(null, "001"));
  }

  @Test
  public void add_pet_to_employee_recent_list_returns_false() {
    assertArrayEquals(employee.getRecents(), new String[0]);
    assertFalse(userDao.addPetToRecents(employee, "001"));
    assertArrayEquals(employee.getRecents(), new String[0]);
    User userdb = userDao.getUserById("001");
    assertEquals(employee, userdb);
  }

  @Test
  public void add_pet_to_bad_webUser_recent_list_returns_false() {
    webUser.setName("Doe");
    assertEquals(webUser.getName(), "Doe");
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToRecents(webUser, "001"));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById("002");
    webUser.setName("Tony Stark");
    assertEquals(webUser.getName(), "Tony Stark");
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_null_pet_to_webUser_recent_list_returns_false() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToRecents(webUser, null));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById("002");
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_inactive_pet_to_webUser_recent_list_returns_false() {
    pet.setActive(false);
    Pet updated_pet = petRepository.save(pet);
    assertEquals(pet, updated_pet);

    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToRecents(webUser, "001"));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById("002");
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_duplicate_pet_to_webUser_recent_list() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    userDao.addPetToRecents(webUser, "001");
    assertArrayEquals(webUser.getRecents(), new String[] {"001"});

    assertFalse(userDao.addPetToRecents(webUser, "001"));
    assertArrayEquals(webUser.getRecents(), new String[] {"001"});

    User userdb = userDao.getUserById("002");
    assertEquals(webUser, userdb);
  }

  @Test
  public void get_webUser_favorites_list() {
    userDao.addPetToFavorites(webUser, "001");
    List<Pet> pet_list = Arrays.asList(new Pet[] {pet});
    assertEquals(userDao.getFavoritePets("002"), pet_list);
  }

  @Test
  public void get_nonexistent_webUser_favorites_list_returns_null() {
    assertNull(userDao.getFavoritePets(null));
  }

  @Test
  public void get_employee_favorites_list_returns_null() {
    assertNull(userDao.getFavoritePets("001"));
  }

  @Test
  public void get_webUser_favorites_list_without_bad_pets() {
    webUser.addToFavorites("001");
    webUser.addToFavorites("010");
    User inserted_user = userRepository.save(webUser);
    assertEquals(webUser, inserted_user);
    assertEquals(userDao.getFavoritePets("002"), Collections.singletonList(pet));
  }

  @Test
  public void get_webUser_recent_list() {
    userDao.addPetToRecents(webUser, "001");
    assertEquals(userDao.getRecentPets("002"), Collections.singletonList(pet));
  }

  @Test
  public void get_nonexistent_webUser_recent_list_returns_null() {
    assertNull(userDao.getRecentPets(null));
  }

  @Test
  public void get_employee_recent_list_returns_null() {
    assertNull(userDao.getRecentPets("001"));
  }

  @Test
  public void get_webUser_recent_list_without_bad_pets() {
    webUser.addToRecents("001");
    webUser.addToRecents("010");
    User inserted_user = userRepository.save(webUser);
    assertEquals(webUser, inserted_user);
    assertEquals(userDao.getRecentPets("002"), Collections.singletonList(pet));
  }
}