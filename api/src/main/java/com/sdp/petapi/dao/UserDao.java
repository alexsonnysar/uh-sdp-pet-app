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
  transient UserRepository repository;

  @Autowired
  transient PetDao petDao;

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

  // does not allow changing of password or email, those should be handled in a different endpoint
  public User putUser(User user) {
    if (user == null) return null;

    User dbUser = getUserById(user.getId());
    if (!canUpdateUser(user, dbUser)) return null;
    
    return repository.save(user);
  }

  private Boolean canUpdateUser(User user, User dbUser) {
    return user.getEmail().equals(dbUser.getEmail())
    && Arrays.equals(user.getFavorites(),dbUser.getFavorites())
    && Arrays.equals(user.getRecents(), dbUser.getRecents())
    && user.isEmployee() == dbUser.isEmployee();
  }

  public User deleteUser(String userid) {
    User user = getUserById(userid);

    if (user == null) return null;
    
    repository.delete(user);
    return user;
  }

  public Boolean addPetToFavorites(String userid, String petid) {
    User user = getUserById(userid);
    if(!isUserValid(user)) return false;

    if(!isPetValid(petid)) return false;

    Boolean result = user.addToFavorites(petid);
    if (result) repository.save(user);
    return result;
  }

  private Boolean isUserValid(User user) {
    if (user == null || user.isEmployee()) return false;

    User userdb = getUserById(user.getId());
    if (!user.equals(userdb)) return false;
    return true;
  }

  private Boolean isPetValid(String petid) {
    Pet pet = petDao.getPetById(petid);
    if (pet == null || !pet.isActive()) return false;
    return true;
  }

  public Boolean removePetFromFavorites(String userid, String petid) {
    User user = getUserById(userid);
    if(!isUserValid(user)) return false;

    Boolean result = user.removeFromFavorites(petid);
    if (result) repository.save(user);
    return result;
  }

  public Boolean addPetToRecents(String userid, String petid) {
    User user = getUserById(userid);
    if (!isUserValid(user)) return false;
    
    if (!isPetValid(petid)) return false;

    Boolean result = user.addToRecents(petid);
    if (result) repository.save(user);
    return result;
  }

  public List<Pet> getFavoritePets(String userid) {
    User user = getUserById(userid);
    return (user == null || user.isEmployee()) ? null : Arrays.asList(user.getFavorites())
      .stream()
      .map(pid -> petDao.getPetById(pid))
      .filter(p -> p != null)
      .collect(Collectors.toList());
  }

  public List<Pet> getRecentPets(String userid) {
    User user = getUserById(userid);
    return (user == null || user.isEmployee()) ? null : Arrays.asList(user.getRecents())
      .stream()
      .map(pid -> petDao.getPetById(pid))
      .filter(p -> p != null)
      .collect(Collectors.toList());
  }

public Boolean existsByEmail(String email) {
	return repository.existsByEmail(email);
}

  public User getUserByEmail(String email) {
    if (email == null) return null;
    
    Optional<User> user = repository.findByEmail(email);
    return user.isPresent() ? user.get() : null;
  }
  
}
