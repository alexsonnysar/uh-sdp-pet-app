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

import com.sdp.petapi.models.User;

import com.sdp.petapi.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  @CrossOrigin
  public List<User> getAllUser() {
    return userService.getAllUsers();
  }

  @PostMapping("/{id}")
  public User getUserById(@PathVariable String id) {
    return userService.getUserById(id);
  }

  @PostMapping("/new")
  public User createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @PutMapping("/{id}")
  public User putUser(@PathVariable String id, @RequestBody User user) {
    return (id == null || user == null || id != user.getId()) ? null : userService.putUser(user);
  }

  @PostMapping("/delete/{id}")
  public User deletePet(@PathVariable String id) {
    return userService.deleteUser(id);
  }

  @PostMapping("/fav/{id}")
  public Boolean addPetToFavorites(@PathVariable String id, @RequestBody User user) {
    return userService.addPetToFavorites(user, id);
  }

  @PutMapping("/fav/{id}")
  public Boolean removePetFromFavorites(@PathVariable String id, @RequestBody User user) {
    return userService.removePetFromFavorites(user, id);
  }

  @PostMapping("/recent/{id}")
  public Boolean addPetToRecents(@PathVariable String id, @RequestBody User user) {
    return userService.addPetToRecents(user, id);
  }
}
