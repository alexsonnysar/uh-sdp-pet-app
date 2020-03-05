package com.sdp.petapi.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
public @Data class Requested {
  private String email;
  private String petID;
  private String userID;
  private Date dateApplied;
  private String status; // Requested, Cancelled, Adopted

}
