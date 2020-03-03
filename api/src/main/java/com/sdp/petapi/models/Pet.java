package com.sdp.petapi.models;

import java.util.*;
import java.util.stream.*;
import org.bson.types.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.*;
import java.util.stream.*;


import org.springframework.data.annotation.Id;


@AllArgsConstructor
@NoArgsConstructor
public @Data class Pet {
<<<<<<< Updated upstream

  
  @Id
=======
  public Pet(String name, String type, String sex, String age, String size, double weight, String description,
			ArrayList<String> imageNames) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.age = age;
        this.size = size;
        this.weight = weight;
        this.description = description;
        this.imageNames = imageNames.stream().collect(Collectors.toSet());
	}
>>>>>>> Stashed changes
  private String id;

  private String name;
  private String type;
  private String sex; // M, F
  private String age; // newborn, young, adult
  private String size; // small, medium, large, extra large
  private Double weight;
  private Date dateAdded;
  private String description;
  private Set<String> imageNames; // link to photos
  private boolean isAdopted;
  private boolean isActive;
<<<<<<< Updated upstream

  public Pet(String name, String type, String sex, String age, String size, Double weight, String desc, ArrayList<String> images){
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.type = type;
    this.sex = sex;
    this.age = age;
    this.size = size;
    this.weight = weight;
    this.dateAdded = new Date();
    this.description = desc;
    this.imageNames = images.stream().collect(Collectors.toSet());
    this.isAdopted = false;
    this.isActive = true;
  }

  public Pet(String id, String name, String type, String sex, String age, String size, Double weight, Date date, String desc, ArrayList<String> images, boolean adopt, boolean status){
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
=======
>>>>>>> Stashed changes

}
