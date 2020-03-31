package com.sdp.petapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class SignupRequest {
  private String email;
  private String password;
  private String name;
}
