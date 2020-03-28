package com.sdp.petapi.models;

import java.util.*;
import java.util.stream.Collectors;

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
  private String name;
  private boolean isEmployee;
  private String[] favorites;
  private String[] recents;

  public User(String email, String passHash) {
    this.email = email;
    this.passHash = passHash;
    this.isEmployee = false;
  }
  
  public User(String email, String name, Boolean employee) {
    this.email = email;
    this.name = name;
    this.isEmployee = employee;
  }
  
  public Boolean addToFavorites(String petid) {
    if (isEmployee) return false;

    if (favorites == null) favorites = new String[0];
    Set<String> temp = Arrays.asList(favorites).stream().collect(Collectors.toSet());
    Boolean result = temp.add(petid);
    if (result) {
      favorites = new String[temp.size()];
      temp.toArray(favorites);
    }
    
    return result;
  }

  public Boolean removeFromFavorites(String petid) {
    if (isEmployee || favorites == null) return false;

    Set<String> temp = new HashSet<String>(Arrays.asList(favorites));
    Boolean result = temp.remove(petid);
    if (result) {
      favorites = new String[temp.size()];
      temp.toArray(favorites);
    }
    
    return result;
  }

  public Boolean addToRecents(String petid) {
    if (isEmployee) return false;
    
    if (recents == null) recents = new String[0];
    Queue<String> temp = new LinkedList<String>(Arrays.asList(recents));
    if (temp.contains(petid)) return false;

    if (temp.size() == 10) temp.remove();
    temp.add(petid);
    recents = new String[temp.size()];
    temp.toArray(recents);
    
    return true;
  }
  
}
