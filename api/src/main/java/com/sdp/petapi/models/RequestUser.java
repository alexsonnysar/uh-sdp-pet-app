package com.sdp.petapi.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
public @Data class RequestUser {
  private Requests request;
  private User user;
}