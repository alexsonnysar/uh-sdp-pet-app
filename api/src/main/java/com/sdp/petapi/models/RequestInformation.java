package com.sdp.petapi.models;

import java.util.*;
import java.util.stream.Collectors;

import com.sdp.petapi.dao.PetDao;
import com.sdp.petapi.dao.UserDao;

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
    this.userName = new UserDao().getUserById(req.getUserid()).getName();
    Pet pet = new PetDao().getPetById(req.getUserid());
    this.petName = pet.getName();
    if (pet.getImageNames() != null && pet.getImageNames().length > 0) this.petImages = pet.getImageNames()[0];
    return this;
  }

  public List<RequestInformation> createPacket(List<Requests> reqList) {
    List<User> userInfo = new UserDao().getAllUsers();
    List<Pet> petInfo = new PetDao().getAllPets();
    
    return reqList.stream().map(r -> new RequestInformation(r.getId(), 
        r.getUserid(), 
        userInfo.stream().filter(u -> u.getId().equals(r.getUserid())).findAny().get().getName(),
        r.getPetid(), 
        petInfo.stream().filter(p -> p.getId().equals(r.getPetid())).findAny().get().getName(), 
        "N/A", //change after images start getting stored
        r.getRequestDate(), 
        r.getStatus())
      ).collect(Collectors.toList());
  }
}