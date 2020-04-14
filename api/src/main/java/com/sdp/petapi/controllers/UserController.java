package com.sdp.petapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.petapi.models.Pet;
import com.sdp.petapi.models.User;
import com.sdp.petapi.security.UserDetailsImpl;
import com.sdp.petapi.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

  @Autowired
  transient UserService userService;

  @GetMapping
  @PreAuthorize("hasRole('Employee')")
  public List<User> getAllUser() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('Employee') or hasRole('User')")
  public User getUserById(@PathVariable String id) {
    // Only Employees can access other users info
    if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Employee"))) {
      return userService.getUserById(id);
    }
    else {
      // Users should not have access to other users info so always return the signed in users own info
      UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return userService.getUserById(userDetails.getId());
    }
  }

  @GetMapping("/info/{email}")
  @PreAuthorize("hasRole('Employee') or hasRole('User')")
  public User getUserByEmail(@PathVariable String email) {
    // Only Employees can access other users info
    if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Employee"))) {
      return userService.getUserByEmail(email);
    }
    else {
      // Users should not have access to other users info so always return the signed in users own info
      UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return userService.getUserByEmail(userDetails.getUsername());
    }
  }

  @GetMapping("/email/{email}")
  public Boolean existsByEmail(@PathVariable String email) {
    return userService.existsByEmail(email);
  }

  @PutMapping("/")
  @PreAuthorize("hasRole('User')")
  public User putUser(@RequestBody User user) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    user.setId(userDetails.getId());
    return userService.putUser(user);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('Employee')")
  public User deleteUser(@PathVariable String id) {
   return userService.deleteUser(id);
  }

  @PostMapping("/fav/{petid}")
  @PreAuthorize("hasRole('User')")
  public Boolean addPetToFavorites(@PathVariable String petid) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userService.addPetToFavorites(userDetails.getId(), petid);
  }

  @PutMapping("/fav/{petid}")
  @PreAuthorize("hasRole('User')")
  public Boolean removePetFromFavorites(@PathVariable String petid) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userService.removePetFromFavorites(userDetails.getId(), petid);
  }

  @PostMapping("/recent/{petid}")
  @PreAuthorize("hasRole('User')")
  public Boolean addPetToRecents(@PathVariable String petid) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userService.addPetToRecents(userDetails.getId(), petid);
  }

  @GetMapping("/fav")
  @PreAuthorize("hasRole('User')")
  public List<Pet> getFavoritePets() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userService.getFavoritePets(userDetails.getId());
  }

  @PutMapping("/recent")
  @PreAuthorize("hasRole('User')")
  public List<Pet> getRecentPets() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userService.getRecentPets(userDetails.getId());
  }
  
}
