package com.sdp.petapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.petapi.dao.UserDao;
import com.sdp.petapi.models.Requested;
import com.sdp.petapi.models.User;

@Service
public class UserService {

  @Autowired
  private UserDao UserDao;

  public List<User> getAllUsers() {
    return UserDao.getAllUsers();
  }

  public User getUserById(String id) {
    return UserDao.getUserById(id);
  }

  public User createUser(User user) {
    return UserDao.createUser(user);
  }

  public User putUser(User user) {
    return UserDao.putUser(user);
  }

  public Boolean deleteUser(String id) {
    return UserDao.deleteUser(id);
  }

  public Requested requestAdoption(Requested request) {
    return UserDao.requestAdoption(request);
  }

}
