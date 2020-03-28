package com.sdp.petapi.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class LoginResponse {
  private String jwt;
  private String id;
  private String email;
  private List<String> roles;
}
