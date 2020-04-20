package com.sdp.petapi.models;

import java.util.*;

import com.sdp.petapi.dao.PetDao;
import com.sdp.petapi.dao.UserDao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
public @Data class RequestInformation {

  private UserDao userDao;
  private PetDao petDao;

  @Id
  private String id;

  private String userId;
  private String userName;
  private String petId;
  private String petName;
  private String petImages;
  private Date requestDate;
  private String status;
  
  public RequestInformation createPacket(Requests req) {
    this.id = req.getId();
    this.userId = req.getUserid();
    this.petId = req.getPetid();
    this.requestDate = req.getRequestDate();
    this.status = req.getStatus();
    this.userName = userDao.getUserById(req.getUserid()).getName();
    Pet pet = petDao.getPetById(req.getUserid());
    this.petName = pet.getName();
    if(pet.getImageNames() != null && pet.getImageNames().length > 0) this.petImages = pet.getImageNames()[0];
    return this;
  }
}