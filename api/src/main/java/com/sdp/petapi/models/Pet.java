package com.sdp.petapi.models;

import java.util.*;
import java.util.stream.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import com.sdp.petapi.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;


@AllArgsConstructor
@NoArgsConstructor
public @Data class Pet {

  @Autowired
  private PetRepository repository;
  
  @Id
  private UUID id;

  private String name;
  private String type;
  private String sex; // M, F
  private String age; // newborn, young, adult
  private String size; // small, medium, large, extra large
  private Double weight;
  private String dateAdded;
  private String description;
  private Set<String> imageNames; // link to photos
  private boolean isAdopted;
  private boolean isActive;

  public Pet(String name, String type, String sex, String age, String size, Double weight, String date, String desc, Collection<String> images){
    while(this.id==null || repository.findAll().stream().filter(p -> p.getId().equals(this.id)).findFirst().get()!=null){
      this.id = UUID.randomUUID();
    }
    this.name = name;
    this.type = type;
    this.sex = sex;
    this.age = age;
    this.size = size;
    this.weight = weight;
    this.dateAdded = date;
    this.description = desc;
    this.imageNames = images.stream().collect(Collectors.toSet());
    this.isAdopted = false;
    this.isActive = true;
  }

  public Pet(UUID id, String name, String type, String sex, String age, String size, Double weight, String date, String desc, Collection<String> images, boolean adopt, boolean status){
    this.id = id;
    this.name = name;
    this.type = type;
    this.sex = sex;
    this.age = age;
    this.size = size;
    this.weight = weight;
    this.dateAdded = date;
    this.description = desc;
    this.imageNames = images.stream().collect(Collectors.toSet());
    this.isAdopted = adopt;
    this.isActive = status;
  }

}
