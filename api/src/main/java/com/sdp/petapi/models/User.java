package com.sdp.petapi.models;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
public @Data class User {
  private String id;
  private String email;
  private String passHash;
  private String firstName;
  private String lastName;
  private boolean isEmployee;

}
