package com.sdp.petapi.models;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
public @Data class RequestInformation {

  @Id
  private String id;

  private String userId;
  private String userName;
  private String userEmail;
  private String petId;
  private String petName;
  private String petImages;
  private Date requestDate;
  private String status;
}