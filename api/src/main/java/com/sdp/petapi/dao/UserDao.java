package com.sdp.petapi.dao;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.petapi.models.*;
import com.sdp.petapi.repositories.UserRepository;

@Component
public class UserDao {

  @Autowired
  private UserRepository repository;

  @Autowired
  private PetDao petDao;

  public List<User> getAllUsers(){
    return repository.findAll();
  }

  public User getUserById(String userid){
    if (userid == null) return null;
    
    Optional<User> user = repository.findById(userid);
    return user.isPresent() ? user.get() : null;
  }

  public User createUser(User user) {
    if(user == null || user.getId() != null) return null;

    return repository.insert(user);
  }

  public User putUser(User user) {
    if(user == null) return null;

    User userdb = getUserById(user.getId());
    return (userdb == null) ? null : repository.save(user);
  }

  public User deleteUser(String userid) {
    User user = getUserById(userid);

    if (user == null) return null;
    
    repository.delete(user);
    return user;
  }

  public Boolean addPetToFavorites(User user, String petid) {
    if (user == null || user.isEmployee()) return false;

    User userdb = getUserById(user.getId());
    if (userdb != user) return false;

    Pet pet = petDao.getPetById(user, petid);
    if (pet == null) return false;

    user.addToFavorites(petid);
    putUser(user);
    return true;
  }

  public Boolean removePetFromFavorites(User user, String petid) {
    if (user == null || user.isEmployee()) return false;
    
    User userdb = getUserById(user.getId());
    if (userdb != user) return false;

    user.removeFromFavorites(petid);
    putUser(user);
    return true;
  }

  public Boolean addPetToRecents(User user, String petid) {
    if (user == null || user.isEmployee()) return false;

    User userdb = getUserById(user.getId());
    if (userdb != user) return false;
    
    Pet pet = petDao.getPetById(user, petid);
    if (pet == null) return false;

    user.addToRecents(petid);
    putUser(user);
    return true;
  }

  public List<Pet> getFavoritePets(User user) {
    if (user == null || user.isEmployee()) return null;

    User userdb = getUserById(user.getId());
    if (userdb != user) return null;

    return Arrays.asList(user.getFavorites())
      .stream()
      .map(pid -> petDao.getPetById(user, pid))
      .collect(Collectors.toList());
  }

  public List<Pet> getRecentPets(User user) {
    if (user == null || user.isEmployee()) return null;

    User userdb = getUserById(user.getId());
    if (userdb != user) return null;

    return Arrays.asList(user.getRecents())
      .stream()
      .map(pid -> petDao.getPetById(user, pid))
      .collect(Collectors.toList());
  }
}
