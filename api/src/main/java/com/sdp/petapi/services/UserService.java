package com.sdp.petapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.petapi.dao.UserDao;

import com.sdp.petapi.models.User;

@Service
public class UserService {

  @Autowired
  private UserDao userDao;

  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }
  
  public User getUserById(String id) {
    return userDao.getUserById(id);
  }

  public User createUser(User user) {
    return userDao.createUser(user);
  }

  public User putUser(User user) {
    return userDao.putUser(user);
  }

  public User deleteUser(String userid) {
    return userDao.deleteUser(userid);
  }

  public Boolean addPetToFavorites(User user, String petid) {
    return userDao.addPetToFavorites(user, petid);
  }

  public Boolean removePetFromFavorites(User user, String petid) {
    return userDao.removePetFromFavorites(user, petid);
  }

  public Boolean addPetToRecents(User user, String petid) {
    return userDao.addPetToRecents(user, petid);
  }
}
