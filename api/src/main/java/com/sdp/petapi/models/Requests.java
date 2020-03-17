package com.sdp.petapi.models;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
public @Data class Requests {
  @Id
  private String id;

  private String userid;
  private String petid;
  private Date requestDate;
  private String status;
  
  public Requests(String userid, String petid) {
    this.userid = userid;
    this.petid = petid;
    requestDate = new Date();
    status = "PENDING";
  }
}