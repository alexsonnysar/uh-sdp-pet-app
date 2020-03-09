package com.sdp.petapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.petapi.models.Message;
import com.sdp.petapi.models.Requested;
import com.sdp.petapi.models.User;

import com.sdp.petapi.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  @CrossOrigin
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable String id) {
    return userService.getUserById(id);
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @PutMapping("/{id}")
  public Message putUser(@PathVariable String id, @RequestBody User user) {
    user.setId(id);
    User returnedUser = userService.putUser(user);
    if (returnedUser != null) {
      return new Message("Updated User");
    } else {
      return new Message("Couldn't update User");
    }
  }

  @DeleteMapping("/{id}")
  public Message deleteUser(@PathVariable String id) {
    if (userService.deleteUser(id)) {
      return new Message("Yeeted User");
    } else {
      return new Message("Couldn't delete User");
    }
  }

  @PostMapping("/requestadoption")
  public Requested requestAdoption(@RequestBody Requested request) {
    return userService.requestAdoption(request);
  }

}
