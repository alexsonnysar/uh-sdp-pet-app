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
  transient UserDao userDao;

  @Autowired
  transient PetRepository petRepository;

  @Autowired
  transient UserRepository userRepository;

  transient Pet pet, pet2;
  transient User employee, webUser;
  private static final String BADID = "010";
  private static final String ID001 = "001";
  private static final String ID002 = "002";
  private static final String AYMEN = "Aymen";
  private static final String PET1 = "pet1";
  private static final String DOE = "Doe";
  private static final String TONY_STARK = "Tony Stark";

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
    String id = ID002;
    User actual_user = userDao.getUserById(id);
    assertEquals(webUser, actual_user);
  }

  @Test
  public void get_bad_pet_by_id_returns_null() {
    String id = BADID;
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

    assertNotNull(inserted_user);
    assertNotNull(inserted_user.getId());
    assertEquals(updated_user_list.size(), orig_user_list.size() + 1);
    assertFalse(orig_user_list.contains(inserted_user));
    assertTrue(updated_user_list.contains(inserted_user));
  }

  @Test
  public void create_user_with_null_user_returns_null() {
    List<User> orig_user_list = userDao.getAllUsers();

    User inserted_user = userDao.createUser(null);
    assertNull(inserted_user);
    assertEquals(userDao.getAllUsers(), orig_user_list);
  }

  @Test
  public void create_user_with_id_returns_null() {
    webUser.setId("003");
    assertEquals(webUser.getId(), "003");
    List<User> orig_user_list = userDao.getAllUsers();

    User inserted_user = userDao.createUser(webUser);
    assertNull(inserted_user);
    assertEquals(userDao.getAllUsers(), orig_user_list);
  }

  @Test
  public void put_user() {
    webUser.setName(AYMEN);
    assertEquals(webUser.getName(), AYMEN);

    List<User> orig_user_list = userDao.getAllUsers();

    User updated_user = userDao.putUser(webUser);
    List<User> updated_user_list = userDao.getAllUsers();
    assertEquals(webUser, updated_user);
    assertEquals(updated_user_list.size(), orig_user_list.size());
    assertFalse(orig_user_list.contains(webUser));
    assertTrue(updated_user_list.contains(webUser));
  }

  @Test
  public void put_user_with_null_user_returns_null() {
    List<User> orig_user_list = userDao.getAllUsers();

    User updated_user = userDao.putUser(null);
    List<User> updated_user_list = userDao.getAllUsers();

    assertNull(updated_user);
    assertEquals(updated_user_list, orig_user_list);
  }

  @Test
  public void put_user_with_wrong_email_returns_null() {
    webUser.setName(AYMEN);
    assertEquals(webUser.getName(), AYMEN);
    webUser.setEmail("4321@mail.com");
    assertEquals(webUser.getEmail(), "4321@mail.com");

    List<User> orig_user_list = userDao.getAllUsers();

    User updated_user = userDao.putUser(webUser);
    List<User> updated_user_list = userDao.getAllUsers();

    assertNull(updated_user);
    assertEquals(updated_user_list, orig_user_list);
  }

  @Test
  public void put_user_with_wrong_favorites_list_returns_null() {
    webUser.setName(AYMEN);
    assertEquals(webUser.getName(), AYMEN);
    webUser.setFavorites(new String[] {PET1});
    assertArrayEquals(webUser.getFavorites(), new String[] {PET1});

    List<User> orig_user_list = userDao.getAllUsers();

    User updated_user = userDao.putUser(webUser);
    List<User> updated_user_list = userDao.getAllUsers();

    assertNull(updated_user);
    assertEquals(updated_user_list, orig_user_list);
  }

  @Test
  public void put_user_with_wrong_recents_list_returns_null() {
    webUser.setName(AYMEN);
    assertEquals(webUser.getName(), AYMEN);
    webUser.setRecents(new String[] {PET1});
    assertArrayEquals(webUser.getRecents(), new String[] {PET1});

    List<User> orig_user_list = userDao.getAllUsers();

    User updated_user = userDao.putUser(webUser);
    List<User> updated_user_list = userDao.getAllUsers();

    assertNull(updated_user);
    assertEquals(updated_user_list, orig_user_list);
  }

  @Test
  public void put_user_with_webUser_acting_as_employee_returns_null() {
    webUser.setName(AYMEN);
    assertEquals(webUser.getName(), AYMEN);
    webUser.setEmployee(true);
    assertTrue(webUser.isEmployee());

    List<User> orig_user_list = userDao.getAllUsers();

    User updated_user = userDao.putUser(webUser);
    List<User> updated_user_list = userDao.getAllUsers();

    assertNull(updated_user);
    assertEquals(updated_user_list, orig_user_list);
  }

  @Test
  public void delete_user() {
    String id = ID002;

    List<User> orig_user_list = userDao.getAllUsers();
    User deleted_user = userDao.deleteUser(id);
    List<User> updated_user_list = userDao.getAllUsers();

    assertEquals(webUser, deleted_user);
    assertTrue(orig_user_list.contains(webUser));
    assertFalse(updated_user_list.contains(webUser));
    assertEquals(orig_user_list.size(), updated_user_list.size() + 1);
  }

  @Test
  public void delete_user_with_null_id_returns_null() {
    List<User> orig_user_list = userDao.getAllUsers();
    User deleted_user = userDao.deleteUser(null);
    List<User> updated_user_list = userDao.getAllUsers();

    assertNull(deleted_user);
    assertEquals(orig_user_list, updated_user_list);
  }

  @Test
  public void delete_nonexistent_user_returns_null() {
    String id = BADID;

    List<User> orig_user_list = userDao.getAllUsers();
    User deleted_user = userDao.deleteUser(id);
    List<User> updated_user_list = userDao.getAllUsers();

    assertNull(deleted_user);
    assertEquals(orig_user_list, updated_user_list);
  }

  @Test
  public void add_pet_to_webUser_favorite_list() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertTrue(userDao.addPetToFavorites(webUser, ID001));
    assertArrayEquals(webUser.getFavorites(), new String[] {ID001});

    User userdb = userDao.getUserById(ID002);
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_pet_to_null_user_favorite_list_returns_false() {
    assertFalse(userDao.addPetToFavorites(null, ID001));
  }

  @Test
  public void add_pet_to_employee_favorite_list_returns_false() {
    assertArrayEquals(employee.getFavorites(), new String[0]);
    assertFalse(userDao.addPetToFavorites(employee, ID001));
    assertArrayEquals(employee.getFavorites(), new String[0]);
    User userdb = userDao.getUserById(ID001);
    assertEquals(employee, userdb);
  }

  @Test
  public void add_pet_to_bad_webUser_favorite_list_returns_false() {
    webUser.setName(DOE);
    assertEquals(webUser.getName(), DOE);
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToFavorites(webUser, ID001));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById(ID002);
    webUser.setName(TONY_STARK);
    assertEquals(webUser.getName(), TONY_STARK);
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_null_pet_to_webUser_favorite_list_returns_false() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToFavorites(webUser, null));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById(ID002);
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_inactive_pet_to_webUser_favorite_list_returns_false() {
    pet.setActive(false);
    Pet updated_pet = petRepository.save(pet);
    assertEquals(pet, updated_pet);

    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToFavorites(webUser, ID001));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById(ID002);
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_duplicate_pet_to_webUser_favorite_list() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    userDao.addPetToFavorites(webUser, ID001);
    assertArrayEquals(webUser.getFavorites(), new String[] {ID001});

    assertFalse(userDao.addPetToFavorites(webUser, ID001));
    assertArrayEquals(webUser.getFavorites(), new String[] {ID001});

    User userdb = userDao.getUserById(ID002);
    assertEquals(webUser, userdb);
  }

  @Test
  public void remove_pet_from_webUser_favorite_list() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    userDao.addPetToFavorites(webUser, ID001);
    assertArrayEquals(webUser.getFavorites(), new String[] {ID001});
    User userdb = userDao.getUserById(ID002);
    assertEquals(webUser, userdb);

    assertTrue(userDao.removePetFromFavorites(webUser, ID001));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User updated_user = userDao.getUserById(ID002);
    assertEquals(webUser, updated_user);
  }

  @Test
  public void remove_pet_from_null_webUser_favorite_list_returns_false() {
    assertFalse(userDao.removePetFromFavorites(null, ID001));
  }

  @Test
  public void remove_pet_from_employee_favorite_list_returns_false() {
    assertArrayEquals(employee.getFavorites(), new String[0]);

    assertFalse(userDao.removePetFromFavorites(employee, ID001));
    assertArrayEquals(employee.getFavorites(), new String[0]);
    User updated_user = userDao.getUserById(ID001);
    assertEquals(employee, updated_user);
  }

  @Test
  public void remove_pet_from_bad_webUser_favorite_list_returns_false() {
    webUser.setName("Davis");
    assertEquals(webUser.getName(), "Davis");

    assertFalse(userDao.removePetFromFavorites(webUser, ID001));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User updated_user = userDao.getUserById(ID002);
    webUser.setName(TONY_STARK);
    assertEquals(webUser.getName(), TONY_STARK);

    assertEquals(webUser, updated_user);
  }

  @Test
  public void remove_nonexistent_petid_from_webUser_favorite_list_returns_false() {
    assertFalse(userDao.removePetFromFavorites(webUser, null));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User updated_user = userDao.getUserById(ID002);
    assertEquals(webUser, updated_user);
  }

  @Test
  public void add_pet_to_webUser_recent_list() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertTrue(userDao.addPetToRecents(webUser, ID001));
    assertArrayEquals(webUser.getRecents(), new String[] {ID001});

    User userdb = userDao.getUserById(ID002);
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_pet_to_null_user_recent_list_returns_false() {
    assertFalse(userDao.addPetToRecents(null, ID001));
  }

  @Test
  public void add_pet_to_employee_recent_list_returns_false() {
    assertArrayEquals(employee.getRecents(), new String[0]);
    assertFalse(userDao.addPetToRecents(employee, ID001));
    assertArrayEquals(employee.getRecents(), new String[0]);
    User userdb = userDao.getUserById(ID001);
    assertEquals(employee, userdb);
  }

  @Test
  public void add_pet_to_bad_webUser_recent_list_returns_false() {
    webUser.setName(DOE);
    assertEquals(webUser.getName(), DOE);
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToRecents(webUser, ID001));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById(ID002);
    webUser.setName(TONY_STARK);
    assertEquals(webUser.getName(), TONY_STARK);
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_null_pet_to_webUser_recent_list_returns_false() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToRecents(webUser, null));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById(ID002);
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_inactive_pet_to_webUser_recent_list_returns_false() {
    pet.setActive(false);
    Pet updated_pet = petRepository.save(pet);
    assertEquals(pet, updated_pet);

    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    assertFalse(userDao.addPetToRecents(webUser, ID001));
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    
    User userdb = userDao.getUserById(ID002);
    assertEquals(webUser, userdb);
  }

  @Test
  public void add_duplicate_pet_to_webUser_recent_list() {
    assert(webUser.getFavorites() == null || webUser.getFavorites().length == 0);
    userDao.addPetToRecents(webUser, ID001);
    assertArrayEquals(webUser.getRecents(), new String[] {ID001});

    assertFalse(userDao.addPetToRecents(webUser, ID001));
    assertArrayEquals(webUser.getRecents(), new String[] {ID001});

    User userdb = userDao.getUserById(ID002);
    assertEquals(webUser, userdb);
  }

  @Test
  public void get_webUser_favorites_list() {
    userDao.addPetToFavorites(webUser, ID001);
    List<Pet> pet_list = Arrays.asList(new Pet[] {pet});
    assertEquals(userDao.getFavoritePets(ID002), pet_list);
  }

  @Test
  public void get_nonexistent_webUser_favorites_list_returns_null() {
    assertNull(userDao.getFavoritePets(null));
  }

  @Test
  public void get_employee_favorites_list_returns_null() {
    assertNull(userDao.getFavoritePets(ID001));
  }

  @Test
  public void get_webUser_favorites_list_without_bad_pets() {
    webUser.addToFavorites(ID001);
    webUser.addToFavorites(BADID);
    User inserted_user = userRepository.save(webUser);
    assertEquals(webUser, inserted_user);
    assertEquals(userDao.getFavoritePets(ID002), Collections.singletonList(pet));
  }

  @Test
  public void get_webUser_recent_list() {
    userDao.addPetToRecents(webUser, ID001);
    assertEquals(userDao.getRecentPets(ID002), Collections.singletonList(pet));
  }

  @Test
  public void get_nonexistent_webUser_recent_list_returns_null() {
    assertNull(userDao.getRecentPets(null));
  }

  @Test
  public void get_employee_recent_list_returns_null() {
    assertNull(userDao.getRecentPets(ID001));
  }

  @Test
  public void get_webUser_recent_list_without_bad_pets() {
    webUser.addToRecents(ID001);
    webUser.addToRecents(BADID);
    User inserted_user = userRepository.save(webUser);
    assertEquals(webUser, inserted_user);
    assertEquals(userDao.getRecentPets(ID002), Collections.singletonList(pet));
  }

  @Test
  public void user_exists_by_email() {
    assertTrue(userDao.existsByEmail("1234@mail.com"));
  }

  @Test
  public void user_exists_by_nonexistent_email_returns_false() {
    assertFalse(userDao.existsByEmail("4321@mail.com"));
  }
}