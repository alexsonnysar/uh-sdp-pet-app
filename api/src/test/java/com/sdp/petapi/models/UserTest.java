package com.sdp.petapi.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sdp.petapi.repositories.PetRepository;

@SpringBootTest
class UserTest {

  @Autowired
  PetRepository petRepo;

  @Test
  public void createWebUser_with_pass() throws Exception {
    User webUser = new User("1234@mail.com", "P4$$vv0rd");

    
    assertAll(
      () -> assertNotNull(webUser),
      () -> assertNull(webUser.getId()),
      () -> assertEquals(webUser.getEmail(), "1234@mail.com"),
      () -> assertEquals(webUser.getPassHash(), "P4$$vv0rd"),
      () -> assertFalse(webUser.isEmployee()),
      () -> assertNull(webUser.getFavorites()),
      () -> assertNull(webUser.getRecents())
    );
  }

  @Test
  public void createWebUser() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    
    assertAll(
      () -> assertNotNull(webUser),
      () -> assertNull(webUser.getId()),
      () -> assertEquals(webUser.getEmail(), "1234@mail.com"),
      () -> assertEquals(webUser.getName(), "kandy Brawn"),
      () -> assertFalse(webUser.isEmployee()),
      () -> assertNull(webUser.getFavorites()),
      () -> assertNull(webUser.getRecents())
    );
  }

  @Test
  public void createEmployee() throws Exception {
    User employee = new User("1234@mail.com", "Mandy Brawn", true);
    
    assertAll(
      () -> assertNotNull(employee),
      () -> assertNull(employee.getId()),
      () -> assertEquals(employee.getEmail(), "1234@mail.com"),
      () -> assertEquals(employee.getName(), "Mandy Brawn"),
      () -> assertTrue(employee.isEmployee()),
      () -> assertNull(employee.getFavorites()),
      () -> assertNull(employee.getRecents())
    );
  }

  @Test
  public void addPetToEmployeeFavorites() throws Exception {
    User webUser = new User("1234@mail.com", "Mandy Brawn", true);
    assertNull(webUser.getFavorites());
    assertFalse(webUser.addToFavorites("124352"));
    assertNull(webUser.getFavorites());
  }

  @Test
  public void addPetToEmptyWebUserFavorites() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    assertNull(webUser.getFavorites());
    assertTrue(webUser.addToFavorites("124352"));
    assertArrayEquals(webUser.getFavorites(), new String[] {"124352"});
  }

  @Test
  public void addPetToExistingWebUserFavorites() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    assertNull(webUser.getFavorites());
    String[] setup = new String[] {"124352"};
    webUser.setFavorites(setup);
    assertArrayEquals(webUser.getFavorites(), setup);

    assertTrue(webUser.addToFavorites("842167"));
    String[] actual = new String[] {"124352", "842167"};
    assertArrayEquals(webUser.getFavorites(), actual);
  }

  @Test
  public void addDuplicatePetToWebUserFavorites() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    assertNull(webUser.getFavorites());
    String[] setup = new String[] {"124352"};
    webUser.setFavorites(setup);
    assertArrayEquals(webUser.getFavorites(), setup);

    assertFalse(webUser.addToFavorites("124352"));
    assertArrayEquals(webUser.getFavorites(), setup);
  }

  @Test
  public void removeFromEmployeeFavorites() throws Exception {
    User webUser = new User("1234@mail.com", "Mandy Brawn", true);
    assertNull(webUser.getFavorites());
    assertFalse(webUser.removeFromFavorites("124352"));
    assertNull(webUser.getFavorites());
  }

  @Test
  public void removePetFromNullWebUserFavorites() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    assertNull(webUser.getFavorites());
    assertFalse(webUser.removeFromFavorites("124352"));
    assertNull(webUser.getFavorites());
  }

  @Test
  public void removePetFromExistingWebUserFavorites() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    assertNull(webUser.getFavorites());
    String[] setup = new String[] {"124352", "842167"};
    webUser.setFavorites(setup);
    assertArrayEquals(webUser.getFavorites(), setup);

    assertTrue(webUser.removeFromFavorites("842167"));
    assertArrayEquals(webUser.getFavorites(), new String[] {"124352"});
  }

  @Test
  public void removePetNotInWebUserFavorites() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    assertNull(webUser.getFavorites());
    String[] setup = new String[] {"124352", "842167"};
    webUser.setFavorites(setup);
    assertArrayEquals(webUser.getFavorites(), setup);

    assertFalse(webUser.removeFromFavorites("453751"));
    assertArrayEquals(webUser.getFavorites(), setup);
  }

  @Test
  public void removePetFromEmptyWebUserFavorites() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    assertNull(webUser.getFavorites());
    assertFalse(webUser.removeFromFavorites("124352"));
    assertNull(webUser.getFavorites());
  }

  @Test
  public void addPetToEmployeeRecents() throws Exception {
    User webUser = new User("1234@mail.com", "Mandy Brawn", true);
    assertNull(webUser.getRecents());
    assertFalse(webUser.addToRecents("124352"));
    assertNull(webUser.getRecents());
  }

  @Test
  public void addPetToEmptyWebUserRecents() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    assertNull(webUser.getRecents());
    assertTrue(webUser.addToRecents("124352"));
    assertArrayEquals(webUser.getRecents(), new String[] {"124352"});
  }

  @Test
  public void addPetToExistingWebUserRecents() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    assertNull(webUser.getRecents());
    String[] setup = new String[] {"124352"};
    webUser.setRecents(setup);
    assertArrayEquals(webUser.getRecents(), setup);

    assertTrue(webUser.addToRecents("842167"));
    assertArrayEquals(webUser.getRecents(), new String[] {"124352", "842167"});
  }

  @Test
  public void checkWebUserRecentsMaxLength() throws Exception {
    User webUser = new User("1234@mail.com", "kandy Brawn", false);
    assertNull(webUser.getRecents());
    String[] setup = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    webUser.setRecents(setup);
    assertArrayEquals(webUser.getRecents(), setup);

    assertTrue(webUser.addToRecents("10"));
    assertArrayEquals(webUser.getRecents(), new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
  }

}
