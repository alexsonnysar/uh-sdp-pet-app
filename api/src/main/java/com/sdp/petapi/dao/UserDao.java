package com.sdp.petapi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.petapi.models.User;
import com.sdp.petapi.repositories.UserRepository;

@Component
public class UserDao {

  @Autowired
  private UserRepository repository;

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
    repository.deleteById(id);
    // maybe check if delete worked?
    return true;
  }

}
