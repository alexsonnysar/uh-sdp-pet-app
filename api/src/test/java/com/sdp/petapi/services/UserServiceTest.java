package com.sdp.petapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.dao.UserDao;
import com.sdp.petapi.models.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
  User user;

  @Mock
  UserDao userDao;

  // makes a userService whose userDao is the mock above
  @InjectMocks
  UserService userService;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    user = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/userObject.json"), User.class);
  }

  @AfterEach
  public void cleanup() {
  }

  @Test
  public void get_all_users() {
    // Since the userDao is a mock it will return null on method calls, so
    // we must specify what it will return given a specific method call
    when(userDao.getAllUsers()).thenReturn(Collections.singletonList(user));
    List<User> list = userService.getAllUsers();
    assertEquals(Collections.singletonList(user), list);
  }

  @Test
  public void get_user_by_id() {
    when(userDao.getUserById(user.getId())).thenReturn(user);
    User returnUser = userService.getUserById(user.getId());
    assertEquals(user, returnUser);
  }

  @Test
  public void create_user() {
    when(userDao.createUser(user)).thenReturn(user);
    User returnUser = userService.createUser(user);
    assertEquals(user, returnUser);
  }

  @Test
  public void put_user() {
    when(userDao.putUser(user)).thenReturn(user);
    User returnedUser = userService.putUser(user);
    assertEquals(user, returnedUser);
  }

  @Test
  public void delete_user() {
    when(userDao.deleteUser(user.getId())).thenReturn(true);
    Boolean deleteSuccess = userService.deleteUser(user.getId());
    assertEquals(true, deleteSuccess);
  }

}