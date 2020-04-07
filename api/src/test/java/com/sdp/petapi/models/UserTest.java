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

  private static final String EMAIL = "1234@mail.com";
  private static final String PASS = "P4$$vv0rd";
  private static final String MANDY = "Mandy Brawn";
  private static final String KANDY = "kandy Brawn";
  private static final String PETID124352 = "124352";
  private static final String PETID842167 = "842167";

  @Test
  public void createWebUser() throws Exception {
    User webUser = new User(EMAIL, PASS);
    
    assertNotNull(webUser);
    assertNull(webUser.getId());
    assertEquals(webUser.getEmail(), EMAIL);
    assertEquals(webUser.getPassHash(), PASS);
    assertFalse(webUser.isEmployee());
    assertNull(webUser.getFavorites());
    assertNull(webUser.getRecents());
  }

  @Test
  public void createEmployee() throws Exception {
    User employee = new User(EMAIL, MANDY, true);
    
    assertNotNull(employee);
    assertNull(employee.getId());
    assertEquals(employee.getEmail(), EMAIL);
    assertEquals(employee.getName(), MANDY);
    assertTrue(employee.isEmployee());
    assertNull(employee.getFavorites());
    assertNull(employee.getRecents());
  }

  @Test
  public void addPetToEmployeeFavorites() throws Exception {
    User webUser = new User(EMAIL, MANDY, true);
    assertNull(webUser.getFavorites());
    assertFalse(webUser.addToFavorites(PETID124352));
    assertNull(webUser.getFavorites());
  }

  @Test
  public void addPetToEmptyWebUserFavorites() throws Exception {
    User webUser = new User(EMAIL, KANDY, false);
    assertNull(webUser.getFavorites());
    assertTrue(webUser.addToFavorites(PETID124352));
    assertArrayEquals(webUser.getFavorites(), new String[] {PETID124352});
  }

  @Test
  public void addPetToExistingWebUserFavorites() throws Exception {
    User webUser = new User(EMAIL, KANDY, false);
    assertNull(webUser.getFavorites());
    String[] setup = new String[] {PETID124352};
    webUser.setFavorites(setup);
    assertArrayEquals(webUser.getFavorites(), setup);

    assertTrue(webUser.addToFavorites(PETID842167));
    String[] actual = new String[] {PETID124352, PETID842167};
    assertArrayEquals(webUser.getFavorites(), actual);
  }

  @Test
  public void addDuplicatePetToWebUserFavorites() throws Exception {
    User webUser = new User(EMAIL, KANDY, false);
    assertNull(webUser.getFavorites());
    String[] setup = new String[] {PETID124352};
    webUser.setFavorites(setup);
    assertArrayEquals(webUser.getFavorites(), setup);

    assertFalse(webUser.addToFavorites(PETID124352));
    assertArrayEquals(webUser.getFavorites(), setup);
  }

  @Test
  public void removeFromEmployeeFavorites() throws Exception {
    User webUser = new User(EMAIL, MANDY, true);
    assertNull(webUser.getFavorites());
    assertFalse(webUser.removeFromFavorites(PETID124352));
    assertNull(webUser.getFavorites());
  }

  @Test
  public void removePetFromNullWebUserFavorites() throws Exception {
    User webUser = new User(EMAIL, KANDY, false);
    assertNull(webUser.getFavorites());
    assertFalse(webUser.removeFromFavorites(PETID124352));
    assertNull(webUser.getFavorites());
  }

  @Test
  public void removePetFromExistingWebUserFavorites() throws Exception {
    User webUser = new User(EMAIL, KANDY, false);
    assertNull(webUser.getFavorites());
    String[] setup = new String[] {PETID124352, PETID842167};
    webUser.setFavorites(setup);
    assertArrayEquals(webUser.getFavorites(), setup);

    assertTrue(webUser.removeFromFavorites(PETID842167));
    assertArrayEquals(webUser.getFavorites(), new String[] {PETID124352});
  }

  @Test
  public void removePetNotInWebUserFavorites() throws Exception {
    User webUser = new User(EMAIL, KANDY, false);
    assertNull(webUser.getFavorites());
    String[] setup = new String[] {PETID124352, PETID842167};
    webUser.setFavorites(setup);
    assertArrayEquals(webUser.getFavorites(), setup);

    assertFalse(webUser.removeFromFavorites("453751"));
    assertArrayEquals(webUser.getFavorites(), setup);
  }

  @Test
  public void removePetFromEmptyWebUserFavorites() throws Exception {
    User webUser = new User(EMAIL, KANDY, false);
    assertNull(webUser.getFavorites());
    assertFalse(webUser.removeFromFavorites(PETID124352));
    assertNull(webUser.getFavorites());
  }

  @Test
  public void addPetToEmployeeRecents() throws Exception {
    User webUser = new User(EMAIL, MANDY, true);
    assertNull(webUser.getRecents());
    assertFalse(webUser.addToRecents(PETID124352));
    assertNull(webUser.getRecents());
  }

  @Test
  public void addPetToEmptyWebUserRecents() throws Exception {
    User webUser = new User(EMAIL, KANDY, false);
    assertNull(webUser.getRecents());
    assertTrue(webUser.addToRecents(PETID124352));
    assertArrayEquals(webUser.getRecents(), new String[] {PETID124352});
  }

  @Test
  public void addPetToExistingWebUserRecents() throws Exception {
    User webUser = new User(EMAIL, KANDY, false);
    assertNull(webUser.getRecents());
    String[] setup = new String[] {PETID124352};
    webUser.setRecents(setup);
    assertArrayEquals(webUser.getRecents(), setup);

    assertTrue(webUser.addToRecents(PETID842167));
    assertArrayEquals(webUser.getRecents(), new String[] {PETID124352, PETID842167});
  }

  @Test
  public void checkWebUserRecentsMaxLength() throws Exception {
    User webUser = new User(EMAIL, KANDY, false);
    assertNull(webUser.getRecents());
    String[] setup = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    webUser.setRecents(setup);
    assertArrayEquals(webUser.getRecents(), setup);

    assertTrue(webUser.addToRecents("10"));
    assertArrayEquals(webUser.getRecents(), new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
  }

}
