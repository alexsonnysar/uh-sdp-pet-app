package com.sdp.petapi.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.User;
import com.sdp.petapi.repositories.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoTest {

  @Autowired
  UserDao userDao;

  @Autowired
  UserRepository userRepository;

  @Mock
  User expected_user;

  User user;

  @Mock
  Message expected_message;

  @BeforeEach
  public void init() throws Exception {
    ObjectMapper om = new ObjectMapper();
    user = om.readValue(new File("src/test/java/com/sdp/petapi/resources/mocks/userObject.json"), User.class);
    userRepository.insert(user);
  }

  @AfterEach
  public void cleanup() {
    userRepository.deleteAll();
  }

  @Test
  public void get_all_users() {
    List<User> actual_users = userDao.getAllUsers();
    assertEquals(Collections.singletonList(user), actual_users);
  }

  @Test
  public void get_user_by_id() {
    User actual_user = userDao.getUserById(user.getId());
    assertEquals(user, actual_user);
  }

  @Test
  public void get_user_by_id_returns_empty_user() {
    // This id should not be in the database
    User actual_user = userDao.getUserById(user.getId() + "999");
    assertEquals(new User(), actual_user);
  }

  @Test
  public void create_user() {
    user.setId(user.getId() + "999");
    user.setEmail("valid@email.com");
    User actual_user = userDao.createUser(user);
    assertEquals(user, actual_user);
  }

  @Test
  public void put_user() {
    user.setFirstName("Changed User");
    User returnedUser = userDao.putUser(user);
    assertEquals(user, returnedUser);

    Optional<User> updatedUser = userRepository.findById(user.getId());
    assertEquals(user, updatedUser.get());
  }

  @Test
  public void delete_user() {
    Boolean deleteSuccess = userDao.deleteUser(user.getId());

    if (deleteSuccess) {
      assertEquals(false, userRepository.existsById(user.getId()));
    } else {
      assertEquals(true, userRepository.existsById(user.getId()));
    }

  }
}