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
  private String userEmail;
  private String petId;
  private String petName;
  private String petImages;
  private Date requestDate;
  private String status;
  
  public RequestInformation createPacket(Requests req) {
    User user = new UserDao().getUserById(req.getUserid());
    Pet pet = new PetDao().getPetById(req.getUserid());

    return new RequestInformation(req.getId(), req.getUserid(), user.getName(), user.getEmail(), req.getPetid(), pet.getName(), 
      (pet.getImageNames() != null && pet.getImageNames().length > 0) ? pet.getImageNames()[0] : "N/A",
      req.getRequestDate(), req.getStatus()
    );
  }

  public List<RequestInformation> createPacket(List<Requests> reqList) {
    List<User> userInfo = new UserDao().getAllUsers();
    List<Pet> petInfo = new PetDao().getAllPets();
    
    return reqList.stream().map(r -> new RequestInformation(r.getId(), 
        r.getUserid(), 
        userInfo.stream().filter(u -> u.getId().equals(r.getUserid())).findAny().get().getName(), 
        userInfo.stream().filter(u -> u.getId().equals(r.getUserid())).findAny().get().getEmail(), 
        r.getPetid(), 
        petInfo.stream().filter(p -> p.getId().equals(r.getPetid())).findAny().get().getName(), 
        "N/A", //change after images start getting stored
        r.getRequestDate(), 
        r.getStatus())
      ).collect(Collectors.toList());
  }
}