package com.sdp.petapi.models;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
public @Data class User {
  @Id
  private String id;

  private String email;
  private String passHash;
  private String firstName;
  private String lastName;
  private boolean isEmployee;
  private Set<String> favorites;
  private Queue<String> recents;
  
  public User(String email, String passHash, String firstName, String lastName, Boolean employee) {
    this.email = email;
    this.passHash = passHash;
    this.firstName = firstName;
    this.lastName = lastName;
    this.isEmployee = employee;
    if (employee) {
        favorites = new HashSet<String>();
        recents = new LinkedList<String>();
    }
  }
  
  public Boolean addToFavorites(String petid) {
    if (isEmployee) return false;

    favorites.add(petid);
    return true;
  }

  public Boolean removeFromFavorites(String petid) {
    if (isEmployee) return false;
    
    return favorites.remove(petid);
  }

  public Boolean addToRecents(String petid) {
    if (isEmployee || recents.contains(petid)) return false;

    if (recents.size() == 10) recents.remove();
    recents.add(petid);
    return true;
  }

}