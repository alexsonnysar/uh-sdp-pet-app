package com.sdp.petapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.petapi.dao.UserDao;
import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.User;

@Service
public class UserService {

  @Autowired
  transient UserDao userDao;

  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }
  
  public User getUserById(String id) {
    return userDao.getUserById(id);
  }

  public User createUser(User user) {
    return userDao.createUser(user);
  }

  public Boolean existsByEmail(String email) {
    return userDao.existsByEmail(email);
  }

  public User putUser(User user) {
    return userDao.putUser(user);
  }

  public User deleteUser(String userid) {
    return userDao.deleteUser(userid);
  }

  public Boolean addPetToFavorites(String userid, String petid) {
    return userDao.addPetToFavorites(userid, petid);
  }

  public Boolean removePetFromFavorites(String userid, String petid) {
    return userDao.removePetFromFavorites(userid, petid);
  }

  public Boolean addPetToRecents(String userid, String petid) {
    return userDao.addPetToRecents(userid, petid);
  }

  public List<Pet> getFavoritePets(String userid) {
    return userDao.getFavoritePets(userid);
  }

  public List<Pet> getRecentPets(String userid) {
    return userDao.getRecentPets(userid);
  }

  public User getUserByEmail(String email) {
    return userDao.getUserByEmail(email);
  }
  
}
