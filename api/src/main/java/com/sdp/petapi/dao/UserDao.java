package com.sdp.petapi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.petapi.models.Requested;
import com.sdp.petapi.models.User;
import com.sdp.petapi.repositories.RequestedRepository;
import com.sdp.petapi.repositories.UserRepository;

@Component
public class UserDao {

  @Autowired
  private UserRepository repository;

  @Autowired
  private RequestedRepository requestedRepository;

  public List<User> getAllUsers() {
    return repository.findAll();
  }

  public User getUserById(String id) {
    Optional<User> user = repository.findById(id);
    if (user.isPresent()) {
      return user.get();
    }
    else {
      return new User();
    }
  }

  public User createUser(User user) {
    return repository.insert(user);
  }

  public User putUser(User user) {
    return repository.save(user);
  }

  public Boolean deleteUser(String id) {
    try {
      repository.deleteById(id);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

public Requested requestAdoption(Requested request) {
  try {
    return requestedRepository.insert(request);
  } catch (Exception e) {
    return new Requested();
  }

}

}
